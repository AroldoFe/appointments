-- Criar usuário postgres com username appointment e senha aleatória
CREATE DATABASE appointments;

CREATE USER appointment_usr WITH PASSWORD 'T9v@x2LpQw7zR1eS';

GRANT CONNECT ON DATABASE appointments TO appointment_usr;

\connect appointments

GRANT USAGE ON SCHEMA public TO appointment_usr;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO appointment_usr;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO appointment_usr;


-- Criar sequence para identificador da tabela user
CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela user
CREATE TABLE "user" (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('user_seq'),
    pub_id VARCHAR(50) NOT NULL,
    name VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(256) NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL DEFAULT now(),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Criar índice único para username da tabela user
CREATE UNIQUE INDEX idx_uq_user_username ON "user"(username) where active = true;
CREATE UNIQUE INDEX idx_uq_user_pub_id ON "user"(pub_id);
CREATE UNIQUE INDEX idx_uq_user_email ON "user"(email) where active = true;

-- Criar sequence para identificador da tabela insurance
CREATE SEQUENCE insurance_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela insurance (convênio)
CREATE TABLE insurance (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('insurance_seq'),
    pub_id VARCHAR(50) NOT NULL,
    name VARCHAR(256) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    creation_timestamp TIMESTAMP NOT NULL default now(),
    creation_user_id BIGINT NOT NULL,
    CONSTRAINT fk_insurance_creation_user FOREIGN KEY (creation_user_id) REFERENCES "user"(id)
);

-- Criar índice único para pub_id da tabela insurance
CREATE UNIQUE INDEX idx_uq_insurance_pub_id ON insurance(pub_id);

-- Criar sequence para identificador da tabela patient
CREATE SEQUENCE patient_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela patient
CREATE TABLE patient (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('patient_seq'),
    pub_id VARCHAR(50) NOT NULL,
    name VARCHAR(256) NOT NULL,
    birth_date DATE NOT NULL,
    document VARCHAR(50) NULL,
    document_type VARCHAR(20) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    creation_timestamp TIMESTAMP NOT NULL default now(),
    creation_user_id BIGINT NOT NULL,
    CONSTRAINT fk_patient_creation_user FOREIGN KEY (creation_user_id) REFERENCES "user"(id)
);

-- Criar índice único para pub_id da tabela patient
CREATE UNIQUE INDEX idx_uq_patient_pub_id ON patient(pub_id);

-- Criar sequence para identificador da tabela patient_history
CREATE SEQUENCE patient_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela patient_history
CREATE TABLE patient_history (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('patient_history_seq'),
    patient_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL DEFAULT now(),
    history TEXT NOT NULL,
    CONSTRAINT fk_patient_history_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
    CONSTRAINT fk_patient_history_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Índices para performance
CREATE INDEX idx_patient_history_patient_id ON patient_history(patient_id);
CREATE INDEX idx_patient_history_user_id ON patient_history(user_id);

-- Criar sequence para identificador da tabela appointment
CREATE SEQUENCE appointment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela appointment
CREATE TABLE appointment (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('appointment_seq'),
    pub_id VARCHAR(50) NOT NULL,
    patient_id BIGINT NOT NULL,
    datetime TIMESTAMP NOT NULL,
    duration INT4 NOT NULL,
    status VARCHAR(50) NOT NULL,
    professional_name VARCHAR(256) NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL DEFAULT now(),
    creation_user_id BIGINT NOT NULL,
    insurance_id BIGINT NOT NULL,
    insurance_card_number VARCHAR(100) NULL,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
    CONSTRAINT fk_appointment_creation_user FOREIGN KEY (creation_user_id) REFERENCES "user"(id),
    CONSTRAINT fk_appointment_insurance FOREIGN KEY (insurance_id) REFERENCES insurance(id)
);

-- Índices para performance na tabela appointment
CREATE INDEX idx_appointment_patient_id ON appointment(patient_id);
CREATE INDEX idx_appointment_creation_user_id ON appointment(creation_user_id);
CREATE INDEX idx_appointment_datetime ON appointment(datetime);
CREATE INDEX idx_appointment_status ON appointment(status);

-- Criar sequence para identificador da tabela patient_phone
CREATE SEQUENCE patient_phone_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela patient_phone
CREATE TABLE patient_phone (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('patient_phone_seq'),
    patient_id BIGINT NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    CONSTRAINT fk_patient_phone_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Índice para performance em patient_id
CREATE INDEX idx_patient_phone_patient_id ON patient_phone(patient_id);

-- Criar sequence para identificador da tabela procedure
CREATE SEQUENCE procedure_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela procedure (procedimento)
CREATE TABLE procedure (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('procedure_seq'),
    pub_id VARCHAR(50) NOT NULL,
    name VARCHAR(256) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    creation_timestamp TIMESTAMP NOT NULL DEFAULT now(),
    creation_user_id BIGINT NOT NULL,
    CONSTRAINT fk_procedure_creation_user FOREIGN KEY (creation_user_id) REFERENCES "user"(id)
);

-- Criar índice único para pub_id da tabela procedure
CREATE UNIQUE INDEX idx_uq_procedure_pub_id ON procedure(pub_id);

CREATE INDEX idx_procedure_creation_user_id ON procedure(creation_user_id);

-- Criar tabela many-to-many entre appointment e procedure
CREATE TABLE appointment_procedure (
    appointment_id BIGINT NOT NULL,
    procedure_id BIGINT NOT NULL,
    PRIMARY KEY (appointment_id, procedure_id),
    CONSTRAINT fk_appointment_procedure_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id),
    CONSTRAINT fk_appointment_procedure_procedure FOREIGN KEY (procedure_id) REFERENCES procedure(id)
);

-- Índices para performance
CREATE INDEX idx_appointment_procedure_appointment_id ON appointment_procedure(appointment_id);
CREATE INDEX idx_appointment_procedure_procedure_id ON appointment_procedure(procedure_id);

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO appointment_usr;