# 🅿️ AVMS - Automated Vehicle Management System 🚙

The Pay And Park is an application that lets parking service provider to register their service and enable customers to login to the system and book the parking space. The system also shows user profile, current booking status,and booking history.

Thus, the system is developed as a generalized solution to provide online platform to register the parking service and let customer book parking space at anytime, from anywhere.

## Configure MySQL database:
	database name: pap
	database username: pap_admin
	database password: pap_admin
	Import the pap.sql(script)
	
## Run App:
	open the project folder in intellij IDEA
	start MySQL database server
	Run 'Main' or Shift+F10
	
	
## Project Structure:
```javascript
pap/
├── databaseHandler
│   ├── DatabaseHandler.java
│   ├── InvalidDataException.java
│   └── userDatabase
│       └── UserDatabase.java
├── entity
│   ├── Address.java
│   ├── BookingLog.java
│   ├── CheckoutLog.java
│   ├── ParkingService.java
│   ├── Payment.java
│   ├── User.java
│   └── Vehicle.java
└── papUser
    ├── controller
    │   ├── AddVehicleController.java
    │   ├── BookingHistoryController.java
    │   ├── BookParkingController.java
    │   ├── CurrentBookingController.java
    │   ├── LoginController.java
    │   ├── LogoutController.java
    │   ├── MainController.java
    │   ├── ProfileController.java
    │   ├── RegisterController.java
    │   └── SavedVehiclesController.java
    ├── Main.java
    ├── ParkingLog.java
    ├── res
    │   ├── images
    │   │   ├── account.png
    │   │   ├── confirm.png
    │   │   ├── insurance.png
    │   │   ├── log-in(1).png
    │   │   ├── log-in.png
    │   │   ├── log-out.png
    │   │   ├── parking(1).png
    │   │   ├── parking-area(1).png
    │   │   ├── parking-area(2).png
    │   │   ├── parking-area(3).png
    │   │   ├── parking-area.png
    │   │   ├── parking.png
    │   │   ├── parking-ticket(1).png
    │   │   ├── parking-ticket.png
    │   │   ├── placeholder.png
    │   │   ├── register.png
    │   │   ├── ticket-machine.png
    │   │   ├── ticket.png
    │   │   ├── user.png
    │   │   └── valet-parking.png
    │   ├── layouts
    │   │   ├── addVehicle.fxml
    │   │   ├── bookingHistory.fxml
    │   │   ├── bookParking.fxml
    │   │   ├── currentBooking.fxml
    │   │   ├── login.fxml
    │   │   ├── logout.fxml
    │   │   ├── main.fxml
    │   │   ├── profile.fxml
    │   │   ├── register.fxml
    │   │   └── savedVehicles.fxml
    │   └── stylesheets
    │       └── style.css
    └── UserData.java
```
