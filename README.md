### 1. **AnswerController** (`/api/answer`)

- No endpoints defined yet

### 2. **FAQController** (`/api/faq`)

- `GET /api/faq/all` - Get all FAQs

### 3. **QuestionController** (`/api/question`)

- `GET /api/question/pending` - Get pending questions
- `GET /api/question/{id}` - Get question by ID
- `GET /api/question/with-answers` - Get all questions with answers
- `GET /api/question/{id}/with-answer` - Get question with answer by ID

### 4. **AppUserController** (`/api/users`)

- `GET /api/users/me` - Get current user info
- `GET /api/users/all` - Get all users

### 5. **ConversationParticipantController** (`/api/conversations/{conversationId}/participants`)

- `GET /api/conversations/{conversationId}/participants` - Get participants for a conversation
- `POST /api/conversations/{conversationId}/participants` - Add participant to conversation

### 6. **ConversationController** (`/api/conversations`)

- `GET /api/conversations` - Get all conversations
- `GET /api/conversations/{id}` - Get conversation by ID

### 7. **LoginController** (`/api/auth`)

- No endpoints defined yet

### 8. **ChatController**

- No endpoints defined yet

### 9. **MessageController** (`/api/conversations/{conversationId}/messages`)

- `GET /api/conversations/{conversationId}/messages` - Get messages for a conversation
- `POST /api/conversations/{conversationId}/messages` - Send a message

### 10. **AuthController** (`/api/auth`)

- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### 11. **PostController** (`/api/posts`)

- `GET /api/posts` - Get all posts
- `GET /api/posts/{id}` - Get post by ID
- `GET /api/posts/user/{userId}` - Get posts by user ID
- `POST /api/posts` - Create a new post
- `PUT /api/posts/{id}` - Update a post
- `DELETE /api/posts/{id}` - Delete a post

### 12. **PostMediaController** (`/api/posts`)

- `GET /api/posts/media` - Get all post media
- `GET /api/posts/{postId}/media` - Get media by post ID
- `GET /api/posts/media/{id}` - Get media by ID
- `POST /api/posts/media` - Create post media
- `PUT /api/posts/media/{id}` - Update post media
- `DELETE /api/posts/media/{id}` - Delete post media

### 13. **AdminPermissionController** (`/api/sysadmin`)

- `GET /api/sysadmin/permissions` - Get all permissions
- `GET /api/sysadmin/permissions/{userId}` - Get permissions for a user
- `POST /api/sysadmin/permissions` - Grant permission to user
- `DELETE /api/sysadmin/permissions/{userId}/{permissionId}` - Revoke permission from user
- `PUT /api/sysadmin/promote/{userId}` - Promote user to admin
- `PUT /api/sysadmin/demote/{userId}` - Demote user to regular user