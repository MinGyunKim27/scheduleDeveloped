CREATE TABLE `user_dev` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `user_name` VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6),
    `updated_at` DATETIME(6),
    PRIMARY KEY (`id`)
) 

CREATE TABLE `todo` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `contents` LONGTEXT NOT NULL,
    `created_at` DATETIME(6),
    `updated_at` DATETIME(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_todo_user` FOREIGN KEY (`user_id`) REFERENCES `user_dev`(`id`)
)

CREATE TABLE `comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `todo_id` BIGINT NOT NULL,
    `contents` VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6),
    `updated_at` DATETIME(6),
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user_dev`(`id`),
    CONSTRAINT `FK_comment_todo` FOREIGN KEY (`todo_id`) REFERENCES `todo`(`id`)
) 