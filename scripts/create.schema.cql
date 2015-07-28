
CREATE KEYSPACE test_migration_1 WITH REPLICATION = {
  'class': 'SimpleStrategy',
  'replication_factor': '1'
  --'Solr': '1'
};

USE test_migration_1;

CREATE TABLE test_from ( 
  main_key uuid,
  name text,
  payload text,
  PRIMARY KEY ( main_key, name)  
);


CREATE TABLE test_to ( 
  main_key uuid,
  name text,
  new_field text,  
  payload text,
  PRIMARY KEY ( (main_key), new_field)  
);


-- dummy data:
INSERT INTO test_from (main_key,name,payload) VALUES (a896fad1-1a8a-4e33-94ba-01f56ed3bf19 , 'zosia', '{"zosia":{"someId":"dassa","OrderStatus":"Completed"}}'); 
INSERT INTO test_from (main_key,name,payload) VALUES (11223344-1a8a-4e33-94ba-01f56ed3bf19 , 'zosia', '{"zosia":{"someId":"12312","OrderStatus":"Completed"}}');
INSERT INTO test_from (main_key,name,payload) VALUES (11223344-1a8a-4e33-94ba-01f56ed3bf19 , 'krysia', '{"krysia":{"id":"12312"}}');

