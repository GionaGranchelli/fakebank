# fakebank Statement Processor Getting Started

### Reference Documentation
For further reference, please consider the following sections:
## Purpose of the project
Validate XML and CSV input representing list of bank statements.
Validation will check validity on unique transaction and on balance.
In addition also other kind of validation has been added, like xml schema validation, or CSV header validation.

## Build
 `./mvnw clean package`
## Profiles available
In order to work in local, you have to run the project in dev mode.
Available profile : 
    `dev, prod`
## INPUT
### CSV
Format:
 
       Reference,Account Number,Description,Start Balance,Mutation,End Balance

### XML
Format: 

    <records>
        <record reference="164702">
             <accountNumber>NL46ABNA0625805417</accountNumber>
             <description>Flowers for Rik Dekker</description>
             <startBalance>81.89</startBalance>
             <mutation>+5.99</mutation>
             <endBalance>87.88</endBalance>
         </record>
    </records>
    
## Output

JSON file containing, list of erroneous statements
    
    {  
        transactionId : String, 
        transactionDescription : String, 
        failureReason : Enum
    }
