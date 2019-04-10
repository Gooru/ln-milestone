-- drop table milestone_queue

-- This queue table is going to be owned by Nucleus and is part of nucleus core DB

CREATE TABLE milestone_queue (
    id bigserial NOT NULL,
    course_id uuid NOT NULL,
    fw_code text NOT NULL,
    override boolean NOT NULL DEFAULT false,
    status int NOT NULL DEFAULT 0 CHECK (status::int = ANY (ARRAY[0::int, 1::int, 2::int])),
    created_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    updated_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    CONSTRAINT mq_pkey PRIMARY KEY (id)
);

ALTER TABLE milestone_queue OWNER TO nucleus;

CREATE UNIQUE INDEX mq_ucc_unq_idx
    ON milestone_queue (course_id, fw_code);

COMMENT on TABLE milestone_queue IS 'Persistent queue for milestone create from course using specified framework';
COMMENT on COLUMN milestone_queue.status IS '0 means queued, 1 means dispatched for processing, 2 means in process';


-- This is the milestone table which maintains map of Milestones to Lessons in specified course
-- This table will be created in nucleus
CREATE TABLE milestone_lesson_map (
  id bigserial NOT NULL PRIMARY KEY,
  milestone_id text NOT NULL,
  course_id uuid NOT NULL,
  unit_id uuid NOT NULL,
  lesson_id uuid NOT NULL,
  grade_id bigint NOT NULL,
  grade_name text NOT NULL,
  grade_seq integer NOT NULL,
  fw_code text NOT NULL,
  tx_subject_code text NOT NULL,
  tx_domain_id bigint NOT NULL,
  tx_domain_seq integer NOT NULL,
  tx_domain_code text NOT NULL,
  tx_comp_code text NOT NULL,
  tx_comp_name text NOT NULL,
  tx_comp_student_desc text,
  tx_comp_seq integer NOT NULL,
  updated_at timestamp without time zone  DEFAULT timezone('UTC'::text, now()) NOT NULL
);

CREATE INDEX mlp_cf_idx ON milestone_lesson_map USING btree (course_id, fw_code);
CREATE INDEX mlp_id_idx ON milestone_lesson_map USING btree (milestone_id);


COMMENT on TABLE milestone_lesson_map IS 'Mapping of milestones to lesson in a specific course for a specified framework. All the tx_* fields are GUT specific ones and not FW specific things';


-- This table will be created in analytics db. The key is not bigserial as key has to match with what is there in nucleus db
CREATE TABLE milestone (
  id bigint NOT NULL PRIMARY KEY,
  milestone_id text NOT NULL,
  course_id uuid NOT NULL,
  unit_id uuid NOT NULL,
  lesson_id uuid NOT NULL,
  fw_code text NOT NULL
);


CREATE INDEX ms_cf_idx ON milestone USING btree (course_id, fw_code);

-- This table will go into DS DB
-- This table represents a data structure to store grade competency map which is hydrated view of grade_competency_bounds
-- It includes competencies only in specific grade and not till that grade.

CREATE TABLE grade_competency_map (
 id bigserial NOT NULL PRIMARY KEY,
 grade_id bigint NOT NULL,
 tx_subject_code text NOT NULL,
 fw_code text NOT NULL,
 tx_domain_id bigint NOT NULL,
 tx_domain_code text NOT NULL,
 tx_domain_seq integer NOT NULL,
 tx_comp_code text NOT NULL,
 tx_comp_name text NOT NULL,
 tx_comp_student_desc text,
 tx_comp_seq integer NOT NULL
);

CREATE INDEX gcm_sf_idx ON grade_competency_map USING btree (tx_subject_code, fw_code);
CREATE INDEX gcm_g_idx ON grade_competency_map USING btree (grade_id);

ALTER TABLE grade_competency_map ADD CONSTRAINT gcm_fk_dcm_sdc FOREIGN KEY (tx_subject_code, tx_domain_code, tx_comp_code) REFERENCES domain_competency_matrix(tx_subject_code, tx_domain_code, tx_comp_code);
ALTER TABLE grade_competency_map ADD CONSTRAINT gcm_fk_gm_gsfw FOREIGN KEY (grade_id, tx_subject_code, fw_code) REFERENCES grade_master(id, tx_subject_code, fw_code);

COMMENT on TABLE grade_competency_map IS 'This table represents a data structure to store grade competency map which is hydrated view of grade_competency_bounds. It includes competencies in specific grade and not till that grade.';


