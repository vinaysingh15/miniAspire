# miniAspire
1. The miniAspire app is built using JAVA, Maven, and SpringBoot framework.
2. FileSystem is used as a Database to store all relevant information.
3. Assumed that the amount is in USD and tenure is in weeks.

#  How to run
1. Install Java
2. Install Maven
3. Update the value of variable LOANS_FOLDER in LoanUtils class to absolute path of a directory.
4. Build the spring-boot app using the command **mvn clean install**
5. Run the spring boot app using the command **mvn spring-boot:run**

# Api Spec
1. Create new loan by customerId & loanId[POST]: **http://baseurl:port/miniAspire/customer/{customerId}/loan/{loanId}**
2. Approve loan by loadId[PUT]: **http://baseurl:port/miniAspire/loan/{loanId}/approve**
3. GET all loans by customerId[GET]: **http://baseurl:port/miniAspire/customer/{customerId}/loans**
4. Repayment[PUT]: **http://baseurl:port/miniAspire/loan/{loanId}/repayment**
5. Attaching the link of POSTMAN collection: https://drive.google.com/file/d/1JUaVIjnKga60oXnsQeiMHBJnm2qLzqSr/view?usp=sharing
