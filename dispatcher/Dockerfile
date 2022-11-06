FROM golang:alpine AS builder
WORKDIR /build
COPY src/. .
RUN go build -o TelegramBot .
FROM alpine
WORKDIR /build
COPY --from=builder /build/TelegramBot /build/TelegramBot
CMD ["./TelegramBot"]