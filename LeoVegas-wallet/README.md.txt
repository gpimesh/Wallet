# About
This project is about  *a small virtual wallet to track users transaction in a account.* When create the user, it will create and account for each user.
User can add or withdraw funds. 

## Technologies
Springboot 2
H2 in memory DB.
JPA
Swagger
maven

##Launch
To run the project, you can import this maven project in to any IDE and run it as an application. 
This springboot application runs on port 8080. If you need to change the port, pls update the 'server.port' property.
The H2 database is available in the path http://localhost:8080/h2 ( no password requred to login)
To test the APIs, you can use the Postman tool.
End point details are available in http://localhost:8080/swagger-ui.html

There are 3 main urls
1. To register user - localhost:8080/wallet/user/registeruser/
2. To credit money - localhost:8080/wallet/transactions/credit/
3. To withdraw money - localhost:8080/wallet/transactions/credit/
4. To see the transactions by user - localhost:8080/wallet/users
5. To see single user details and current balance - localhost:8080/api/user/{userID}

Below are some sample json requests.

### To create a user.
When you create the user you can pass the entry amount to the account. Otherwise you can just pass it as 0, and update later using "wallet/transactions/credit/" API.
A post request to URL - localhost:8080/wallet/user/registeruser/
The userID and the email is unique. It cannot be duplicated. Minimum char length for user ID is 5.
```{JSON}
{
	"userID": "xxxxh",
	"fName" : "Dsd",
	 "lName" : "cccc",
	"email" :"danqsh@brown.com",
	"account": {
        		"balance":0
    }
}

```

### To withdraw funds
A post request to URL : localhost:8080/wallet/transactions/withdraw/
```{JSON}
{
    "userid": "xxxxh",
    "transactionID": "10001",
    "amount": "1500.0"

}
```
Note: make sure you match the userID with the user you created before. Otherwise you will get a user not availabe error  


### To add funds 
A post request to URL : localhost:8080/wallet/transactions/credit/
```{JSON}
{
    "userid": "xxxxh",
    "transactionID": "10003",
    "amount": "1000.0"

}
```
### To see the transaction list by user
Send a get request to URL localhost:8080/wallet/users

### To see selected user's details with the current balance.

Send a GET requet to URL : localhost:8080/api/user/{userID}

```{sql}
select * from users;
select * from account;
select * from transactions