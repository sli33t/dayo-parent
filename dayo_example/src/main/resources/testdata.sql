INSERT INTO OAUTH_CLIENT_DETAILS(
CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, 
AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
VALUES ('myapp', 'order','{bcrypt}$2a$10$Y8OCt2WQRwBIw8pHpRDQGO9hSdi848Y3FTu419B6.M7/79fGmoKyu',
'all', 'password,refresh_token', 'ADMIN', 10800, 2592000);

--密码是123456加密得来的
INSERT INTO tb_User (User_ID, TEL_NO, User_TYpe, User_Name, PASSWORD) 
VALUES(1, '13588888888', 0, 'admin', 
'$2a$10$72mqro/AGy1pGc6YcI7tQuqJLp17.A/sV320O.iE1nQ2Z3wJ/piMa')


