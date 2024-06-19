CREATE TABLE `ideas` (
  `idea_id` int PRIMARY KEY,
  `user_id` int,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `status` enum('pending','accepted','rejected') DEFAULT 'pending',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `modified_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` varchar(255)
);

CREATE TABLE `users` (
  `user_id` int PRIMARY KEY,
  `email` varchar(255) UNIQUE NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(255),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `comments` (
  `comment_id` int PRIMARY KEY,
  `idea_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `votes` (
  `vote_id` int PRIMARY KEY AUTO_INCREMENT,
  `idea_id` int NOT NULL,
  `user_id` int NOT NULL,
  `vote_type` enum('upvote','downvote') NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `challenges` (
  `challenge_id` int PRIMARY KEY,
  `title` varchar(255),
  `challenge` text
);

-- INDEXES --
CREATE UNIQUE INDEX `comments_index_0` ON `comments` (`idea_id`, `user_id`);
CREATE UNIQUE INDEX `votes_index_1` ON `votes` (`idea_id`, `user_id`);

-- foreign keys --
ALTER TABLE `ideas` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE `ideas` ADD FOREIGN KEY (`modified_by`) REFERENCES `users` (`user_id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`idea_id`) REFERENCES `ideas` (`idea_id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE `votes` ADD FOREIGN KEY (`idea_id`) REFERENCES `ideas` (`idea_id`);
ALTER TABLE `votes` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);