
GET /api/users/all

Response:
[
{
id: number
email: string
status: string
role: string
is_online: boolean
}
]

Get api/users/me
Response:
{
email: string
}


GET /api/question/pending

Response:
[
{
id: number
nickname: string
text: string
sentAt: datetime
status: string
}
]

GET /api/question/with-answers

Response:
[
{
questionId: number
nickname: string
questionText: string
answerText: string | null
}
]

GET /api/question/{appUserId}/with-answer
Response:

{
questionId: number
nickname: string
questionText: string
answerText: string | null
}

GET /api/conversations/{questionId/messages
Response:
[
{
id: number
senderId: string
content: string
sentAt: string
}
]
