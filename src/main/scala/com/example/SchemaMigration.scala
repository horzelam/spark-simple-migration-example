package com.example



import pro.foundev.scala._

object SchemaSimpleMigration extends CassandraCapable {
  def getFullMigratedTable(): String = {
    s"${getKeySpaceName()}.test_to"
  }

  def main(args: Array[String]): Unit = {
    printf("Running migration:...")
    val context = connectToCassandra()
    val connector = context.connector;
    
    
    context.rdd.map(row => {

      val id = row.getUUID("main_key")
      val name = row.getString("name")
      val payload = row.getString("payload")

      connector.withSessionDo(session => {
        //  main_key uuid,
        //  name text,
        //  new_field text,  
        //  payload text,
        val statement = session.prepare(s"INSERT INTO ${getFullMigratedTable()} (main_key, name, new_field, payload) " +
          "values (?, ?, ?, ?)")
        val bound = statement.bind(id, name, "?migrated?", payload)
        session.executeAsync(bound)
      })
    }).foreach(x => x.getUninterruptibly())
  }
}