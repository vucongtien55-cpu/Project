CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    sex BOOLEAN NOT NULL, -- TRUE: Nam, FALSE: Nữ
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT'
        CHECK (role IN ('ADMIN', 'STUDENT')),
    password VARCHAR(255) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    duration INT NOT NULL check (duration > 0),
    instructor VARCHAR(100) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE enrollment (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'WAITING'
        CHECK (status IN ('WAITING', 'DENIED', 'CANCER', 'CONFIRM')),

    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id)
        REFERENCES student(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_enrollment_course
        FOREIGN KEY (course_id)
        REFERENCES course(id)
        ON DELETE CASCADE
);

create table admin(
	id serial primary key,
 	username varchar(50) unique not null,
	password varchar(100) not null
);

INSERT INTO student(name, dob, email, sex, phone, password)
VALUES
('Nguyen Van A', '2000-05-10', 'a@gmail.com', TRUE, '0901234567', '123456'),
('Tran Thi B', '2001-08-20', 'b@gmail.com', FALSE, '0912345678', '123456');

INSERT INTO course(name, duration, instructor)
VALUES
('Java Core', 40, 'Nguyen Van Giang'),
('Spring Boot', 60, 'Tran Van Hung');

INSERT INTO enrollment(student_id, course_id)
VALUES
(1, 1),
(2, 2);

insert into admin(username, password) 
values
('admin', '123');