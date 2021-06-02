CREATE TABLE notification
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  notifier BIGINT NOT NULL,
  receiver BIGINT NOT NULL,
  outerId BIGINT,
  type INT,
  status INT DEFAULT 0 NOT NULL,
  gmt_create BIGINT
);