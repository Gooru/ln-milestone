
-- Nucleus DB
ALTER TABLE milestone_lesson_map add column tx_domain_name text;

-- DSDB
ALTER TABLE grade_competency_map add column tx_domain_name text;
UPDATE grade_competency_map SET tx_domain_name = (SELECT tx_domain_name FROM tx_domains WHERE id = tx_domain_id);
ALTER TABLE grade_competency_map ALTER COLUMN tx_domain_name SET NOT NULL;
