$body = @{
    name  = "PS User"
    email = "ps@example.com"
} | ConvertTo-Json

$user = Invoke-RestMethod "http://localhost:8080/users" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body

Invoke-RestMethod "http://localhost:8080/users/1"