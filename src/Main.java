public static void main(String[] args) {
//Create a Registration object
    Registration registration = new Registration();
    registration.Register();

//Create Login object using registered details
    Login login = new Login(registration.userName, registration.password, registration.firstName, registration.lastName);
    login.loginProcess();
}