**Create a new Customer**
----
  Allows to register a customer in the System.

* **URL**

  `/api/v1/customers`

* **Method:**

  `POST`
  
*  **URL Params**

   No URL Parameters Required 

* **Data Params**

  `{
     "email": "Email ID",
     "firstName": "First Name",
     "lastName": "Last Name",
     "phoneNumber": 12345
   }`

* **Success Response:**

  * **Code:** 201 <br />
    **Content:** `{
                    "createdDate": "2019-07-31T13:37:50.854Z",
                    "modifiedDate": "2019-07-31T13:37:50.854Z",
                    "accountList": null,
                    "id": 1,
                    "firstName": "string",
                    "lastName": "string",
                    "phoneNumber": "12345",
                    "email": "string"
                  }`
 
* **Error Response:**

  * **Code:** 409 CONFLICT <br />
    **Content:** `{
                    "timestamp": "2019-07-31T14:46:54.248+0000",
                    "status": 409,
                    "errors": [
                      "Customer with the given email already exists:string"
                    ]
                  }`
* **Notes:**

  This API expects to have unique email Id for each customer.
  It will not allow to create a new customer with the same Email ID. 
  
**Create a new Account**
----
  This api allows to create a new account of an already Existing Customer.
  Account can be of type CURRENT or SAVINGS.

* **URL**

  /api/v1/customers/{id}/accounts

* **Method:**

  `POST`
  
*  **URL Params** 

    This represents Customer Id for which a new account is to be created.
   **Required:**
 
   `id=[integer]` 

* **Data Params**

  `{
     "balance": 100,
     "type": "CURRENT"
   }`

* **Success Response:**

  * **Code:** 201 <br />
    **Content:** `{
                    "createdDate": "2019-07-31T14:58:00.365Z",
                    "modifiedDate": "2019-07-31T14:58:00.365Z",
                    "id": 2,
                    "balance": 0,
                    "type": "CURRENT"
                  }`
 
* **Error Response:**

  * **Code:** 409 CONFLICT <br />
    **Content:** `{
                    "timestamp": "2019-07-31T14:58:37.798+0000",
                    "status": 409,
                    "errors": [
                      "Account already exists for customer:1"
                    ]
                  }`
  * **Code:** 404 NOT_FOUND <br />
      **Content:** `{
                      "timestamp": "2019-07-31T14:58:37.798+0000",
                      "status": 404,
                      "errors": [
                        "Customer Does not exist:String"
                      ]
                    }`
                  
* **Notes:**

  Customer can have accounts of type CURRENT or SAVINGS.
  Customer can have only one of each type.
  API won't allow to create same type of account twice.
  
**Perform a transaction**
----
  This API allows to perform transaction against any Account id.
  Transaction can be of type CREDIT/DEBIT or INITIAL.
  INITIAL is the type of transaction issued on the account opening request.

* **URL**

  /api/v1/accounts/{id}/transactions
  
* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  `{
     "amount": 1000,
     "type": "CREDIT"
   }`

* **Success Response:**

  * **Code:** 201 <br />
    **Content:** `{
                    "createdDate": "2019-07-31T15:04:00.725Z",
                    "modifiedDate": "2019-07-31T15:04:00.725Z",
                    "id": 2,
                    "type": "CREDIT",
                    "transactionDate": "2019-07-31T15:04:00.725Z",
                    "amount": 200,
                    "status": "SUCCESS"
                  }`
 
* **Error Response:**

  * **Code:** 404 NOT_FOUND <br />
    **Content:** `{
                    "timestamp": "2019-07-31T15:04:54.850+0000",
                    "status": 404,
                    "errors": [
                      "Account Number is not Valid:134"
                    ]
                  }`
  * **Code:** 400 BAD_REQUEST <br />
      **Content:** `{
                      "timestamp": "2019-07-31T15:04:54.850+0000",
                      "status": 400,
                      "errors": [
                        "Not Enough balance"
                      ]
                    }`
                  
**Get Customer Details**
----
  API allows to fetch Customer's personal details along with Account details & Transaction Details.

* **URL**

  /api/v1/customers/{id}

* **Method:**
 
  `GET`
  
*  **URL Params** 

   **Required:**
 
   `id=[integer]`

* **Success Response:**
  

  * **Code:** 200 <br />
    **Content:** `{
                    "accountList": [
                      {
                        "balance": 0,
                        "createdDate": "2019-07-31T15:11:11.730Z",
                        "id": 0,
                        "modifiedDate": "2019-07-31T15:11:11.730Z",
                        "transactionList": [
                          {
                            "amount": 0,
                            "createdDate": "2019-07-31T15:11:11.730Z",
                            "id": 0,
                            "modifiedDate": "2019-07-31T15:11:11.730Z",
                            "status": "SUCCESS",
                            "transactionDate": "2019-07-31T15:11:11.730Z",
                            "type": "CREDIT"
                          }
                        ],
                        "type": "CURRENT"
                      }
                    ],
                    "createdDate": "2019-07-31T15:11:11.730Z",
                    "email": "string",
                    "firstName": "string",
                    "id": 0,
                    "lastName": "string",
                    "modifiedDate": "2019-07-31T15:11:11.730Z",
                    "phoneNumber": "string"
                  }`
 
* **Error Response:**

  * **Code:** 404 NOT_FOUND <br />
    **Content:** `{
                    "timestamp": "2019-07-31T15:14:27.191+0000",
                    "status": 404,
                    "errors": [
                      "Customer Id is not found:5"
                    ]
                  }`
 
 
**For a complete list of available APIs, browse to /swagger-ui.html page**