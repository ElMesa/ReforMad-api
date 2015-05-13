-- Table: issue

-- DROP TABLE issue;

CREATE TABLE issue
(
  id bigserial NOT NULL,
  latitude real,
  longitude real,
  CONSTRAINT issue_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE issue
  OWNER TO postgres;