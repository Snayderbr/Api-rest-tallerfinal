TALLER FINAL - Desarrollo de Software Seguro (Spring Boot + Spring Security + SQLite)
1) Abrir la carpeta en VS Code (Extension Pack for Java + Spring Boot Extension Pack) y correr EmpleadosApplication.
   La base de datos se crea sola: archivo taller.db en la raiz del proyecto.
2) Para ver las tablas: extension "SQLite Viewer" de VS Code, clic sobre taller.db.
3) Usuarios de prueba (Postman > Authorization > Basic Auth):
   admin / admin123      (ROLE_ADMIN)
   usuario / usuario123  (ROLE_USER)
Endpoints:
  POST   /api/auth/registro   (publico)  {"username":"x","password":"123456"}
  GET    /api/usuarios        (admin)
  POST   /api/usuarios        (admin)    {"username":"x","password":"123456","roles":["ADMIN"]}
  GET    /api/empleados       (admin/user)
  GET    /api/empleados/{id}  (admin/user)
  POST   /api/empleados       (admin/user)
  PUT    /api/empleados/{id}  (admin/user)
  DELETE /api/empleados/{id}  (solo admin)
Body ejemplo empleado:
  {"nombre":"Ana","apellido":"Gomez","email":"ana@mail.com","cargo":"Dev","salario":4500000}
