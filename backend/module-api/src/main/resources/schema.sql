SET foreign_key_checks = 0;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS quiz CASCADE;
DROP TABLE IF EXISTS quiz_solved_state CASCADE;
DROP TABLE IF EXISTS quiz_log CASCADE;
DROP TABLE IF EXISTS quiz_type_mapping CASCADE;

CREATE TABLE users
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    nickname            varchar(20)     DEFAULT NULL COMMENT '닉네임',
    profile_image       varchar(250)    DEFAULT NULL COMMENT '프로필 사진',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id),
    KEY users_idx_nickname (nickname)
) COMMENT '유저 테이블';

CREATE TABLE quizzes
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    title               varchar(50)     NOT NULL COMMENT '문제 제목',
    quiz_url            varchar(250)    DEFAULT NULL COMMENT '문제 URL',
    level               int             NOT NULL COMMENT '문제레벨 (실버, 골드, 레벨2, 레벨3)',
    platform            int             NOT NULL COMMENT '문제 플랫폼 (백준, 프로그래머스)',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id)
) COMMENT '퀴즈 테이블';

CREATE TABLE quiz_solved_states
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    solved_state        int             NOT NULL COMMENT '문제풀이 상태 (안뽑음 / 풀이 미완료 / 풀이 완료)',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    user_id             bigint          DEFAULT NULL COMMENT 'user id',
    quiz_id             bigint          DEFAULT NULL COMMENT 'quiz id',
    PRIMARY KEY (id),
    KEY quiz_solved_state_idx_user (user_id),
    KEY quiz_solved_state_idx_solved_state (solved_state),
    CONSTRAINT fk_quiz_solved_state_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_quiz_solved_state_to_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE SET NULL ON UPDATE CASCADE
) COMMENT '문제 풀이 상태 테이블';

CREATE TABLE quiz_logs
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    solved_state        int             NOT NULL COMMENT '문제풀이 상태 (안뽑음 / 풀이 미완료 / 풀이 완료)',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    user_id             bigint          DEFAULT NULL COMMENT 'user id',
    quiz_id             bigint          DEFAULT NULL COMMENT 'quiz id',
    PRIMARY KEY (id),
    KEY quiz_log_idx_user (user_id),
    KEY quiz_log_idx_quiz (quiz_id),
    CONSTRAINT fk_quiz_log_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_quiz_log_to_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE SET NULL ON UPDATE CASCADE
) COMMENT '문제 풀이 상태 로그 테이블';

CREATE TABLE quiz_type_mappings
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    quiz_id             bigint          DEFAULT NULL COMMENT 'quiz id',
    quiz_type           int             NOT NULL COMMENT '알고리즘 유형 (DP, DFS, BFS 등)',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id),
    KEY quiz_type_mapping_idx_quiz (quiz_id),
    CONSTRAINT fk_quiz_type_mapping_to_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE SET NULL ON UPDATE CASCADE
) COMMENT '문제 유형 매핑 테이블';

SET foreign_key_checks = 1;