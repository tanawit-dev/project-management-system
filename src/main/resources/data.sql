INSERT INTO employee(id, first_name, last_name, role,  username, password) VALUES (1, 'Staff', '1', 'STAFF', 'staff1', '$2y$12$B/Sj.lyHZz9SO658LnwIFumVQr8v.zTE8Bry24HRpPjNX/8LAXwWm');
INSERT INTO employee(id, first_name, last_name, role,  username, password) VALUES (2, 'Staff', '2', 'STAFF', 'staff2', '$2y$12$B/Sj.lyHZz9SO658LnwIFumVQr8v.zTE8Bry24HRpPjNX/8LAXwWm');
INSERT INTO employee(id, first_name, last_name, role,  username, password) VALUES (3, 'Staff', '3', 'STAFF', 'staff3', '$2y$12$B/Sj.lyHZz9SO658LnwIFumVQr8v.zTE8Bry24HRpPjNX/8LAXwWm');
INSERT INTO employee(id, first_name, last_name, role,  username, password) VALUES (4, 'Manager', '1', 'MANAGER', 'manager1', '$2y$12$B/Sj.lyHZz9SO658LnwIFumVQr8v.zTE8Bry24HRpPjNX/8LAXwWm');
INSERT INTO employee(id, first_name, last_name, role,  username, password) VALUES (5, 'Manager', '2', 'MANAGER', 'manager2', '$2y$12$B/Sj.lyHZz9SO658LnwIFumVQr8v.zTE8Bry24HRpPjNX/8LAXwWm');

INSERT INTO project(id, name) VALUES (1, 'Project 1');
INSERT INTO project(id, name) VALUES (2, 'Project 2');
INSERT INTO project(id, name) VALUES (3, 'Project 3');

INSERT INTO task(id, name, project_id, employee_id) VALUES (1, 'Task 1', 1, 1);
INSERT INTO task(id, name, project_id) VALUES (2, 'Task 2', 1);
INSERT INTO task(id, name, project_id) VALUES (3, 'Task 3', 1);
INSERT INTO task(id, name, project_id) VALUES (4, 'Task 4', 2);

INSERT INTO employee_project(id, join_at, employee_id, project_id) VALUES (1, '2021-01-04 15:40:43.81+07', 1, 1);