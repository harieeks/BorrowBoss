# Borrow Boss - Rental service web application

## Overview
This project aims to create a rental service platform, providing a space for users to post items for rent and enables others to rent those items.
We can book those items as per a advance amount.

## Features

- **Location-Based Item Search:**
  
    Users can easily search for items available for rent in specific locations, making the process convenient and tailored to their needs.
    With the help of geocoder
  
- **Post Items in Specific Locations:**
  
    Item owners can post details about items they want to rent out, specifying the location where the items are available.

- **Real-Time Chat:**
  
   Enable real-time communication between item owners and customers through a built-in chat feature, streamlining the rental process and fostering quick and 
   efficient 
   

- **User Authentication:**
  
    Secure user registration and authentication.
    Support for both email/password and Google Authentication.

- **Payment Integration:**

   Secure payment processing using Stripe.
   Transparent handling of transactions with clear invoicing.

- **Admin Panel:**

   Admin interface for managing users, items, and bookings

## Technology Stack

- **Frontend:**
  - Angular for building interactive and dynamic user interfaces.
  - Ngrx for state management.

- **Backend:**
  - Java Spring Boot for server side application
  - Database (PostgreSQL, Mongodb)

- **Microservice Architecture:**
  - Utilizes a microservices architecture for scalability and maintainability.
  - Services include User management service, Item service, messaging service and more.

- **External services:**
  - This project utilizes a geocoder to convert addresses into geographic coordinates.
  - The geocoder is responsible for translating user-input addresses into latitude and longitude coordinates, enabling location- 
    based functionalities within the application.

- **Real time communication:**
  - This project includes real-time chat functionality implemented using WebSocket technology.

  
 ## Deployment 

 - The project can be deployed on cloud platforms such as AWS or Azure.
 - Docker containers for easy deployment and scaling.

## Related Documentations

- [Figma design](https://www.figma.com/file/8x90B79nKwLsBy8P03T3Zk/Untitled?type=design&node-id=0-1&mode=design&t=z394U7iYKXNcQ8ZH-0)
- [Microservice daigram](https://app.diagrams.net/#G1grZj6WXUp6JmIAO6m45hkpoaH08GThgS).
- [Database Design](https://drive.google.com/file/d/1DIfiOfhrdu3QCKxbQ84H_OTXrc3ZB70u/view?usp=drive_link).
- [Api Documentation](https://documenter.getpostman.com/view/27807383/2s9YeN3ovy#0a3bd0d5-a4ec-442e-b289-60daa0b851b6).

