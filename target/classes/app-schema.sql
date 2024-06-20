-- Users table
CREATE TABLE `users` (
  `user_id` INT PRIMARY KEY AUTO_INCREMENT,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) UNIQUE NOT NULL,
   `role` varchar(255),
  `department` VARCHAR(255) NOT NULL,
  `state` VARCHAR(255) NOT NULL,
  `branch` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` INT NULL
)

-- Ideas table
CREATE TABLE `ideas` (
  `idea_id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
 `status` enum('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` INT NULL,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`created_by`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`updated_by`) REFERENCES `users`(`user_id`)
)

-- Comments table
CREATE TABLE `comments` (
  `comment_id` int PRIMARY KEY,
  `idea_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT NOT NULL,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` INT NULL
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`idea_id`) REFERENCES `ideas` (`idea_id`),
    FOREIGN KEY (`created_by`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`updated_by`) REFERENCES `users`(`user_id`)
)

-- Votes table
CREATE TABLE `votes` (
  `vote_id` int PRIMARY KEY AUTO_INCREMENT,
  `idea_id` int NOT NULL,
  `user_id` int NOT NULL,
  `vote_type` enum('UPVOTE','DOWNVOTE') NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT NOT NULL,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` INT NULL,
   FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
   FOREIGN KEY (`idea_id`) REFERENCES `ideas` (`idea_id`)
)

-- Challenges table
CREATE TABLE `challenges` (
  `challenge_id` int PRIMARY KEY,
  `title` varchar(255),
  `challenge` text,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT NOT NULL,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` INT NULL
)

-- INDEXES --
--CREATE UNIQUE INDEX `comments_index_0` ON `comments` (`idea_id`, `user_id`);
--CREATE UNIQUE INDEX `votes_index_1` ON `votes` (`idea_id`, `user_id`);