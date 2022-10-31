package main

type Update struct {
	UpdateId int	`json:"update_id"`
	Message Message	`json:"message"`
}

type Message struct {
	Chat Chat			`json:"chat"`
	Text string			`json:"text"`
	Photo []PhotoSize	`json:"photo"`
	Doc Document		`json:"document"`
}

type Chat struct {
	ChatId int		`json:"id"`
}

type RestResponse struct {
	Result []Update	`json:"result"`
}

type PhotoSize struct {
	FileId string		`json:"file_id"`
	FileUniqueId string	`json:"file_unique_id"`
	Width int			`json:"width"`
	Height int			`json:"height"`
	FileSize int		`json:"file_size"`
}

type Document struct {
	FileId string		`json:"file_id"`
	FileUniqueId string	`json:"file_unique_id"`
	Thumb PhotoSize		`json:"thumb"`
	FileName string		`json:"file_name"`
	MimeType string		`json:"mime_type"`
	FileSize int		`json:"file_size"`
}

type BotMessage struct {
	ChatId int	`json:"chat_id""`
	Text string	`json:"text"`
}