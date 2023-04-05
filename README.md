# Game Store Inventory Management Web Service   
Authors: Katherin Jimenez, Evelyn Quintero Ochoa, Lilie Bingham   
References: https://nullbeans.com/the-java-bean-validation-cheet-sheet/ , https://stackoverflow.com/questions/26548505/org-hibernate-hibernateexception-access-to-dialectresolutioninfo-cannot-be-null


User Stories: 

- As a user, I would like to be able to create, read, update, and delete game information.

- As a user, I would like to be able to search for games by studio, ESRB rating, and title.

- As a user, I would like to be able to create, read, update, and delete console information.

- As a user, I would like to be able to search for consoles by manufacturer.

- As a user, I would like to be able to create, read, update, and delete T-shirt information.

- As a user, I would like to be able to search for games by color and size.

- As a user, I would like to be able to purchase a specified quantity of products (games, consoles, T-shirts) and an invoice will be created that includes any taxes and processing fees.


API Features:  

This system manages the inventory of video games, game consoles, and T-shirts.

The REST API allows the end user to do the following in each category:

  Games:

  - Perform standard CRUD operations for games.

  - Search for games by studio.

  - Search for games by ESRB rating.

  - Search for games by title.

  Consoles:

  - Perform standard CRUD operations for consoles.

  - Search for consoles by manufacturer.

  T-shirts:

  - Perform standard CRUD operations for T-shirts.

  - Search for T-shirts by color.

  - Search for T-shirts by size.

  Invoices:

  Create an invoice by supplying the following information to the endpoint:

  - Name

  - Street

  - City

  - State

  - Zip

  - Item type

  - Item ID

  - Quantity


Also supports GraphQL queries to retrieve the following information:

- Get all Games

- Get a Game by ID

- Get a Game by Title

- Get a Game by ESRB rating

- Get a Game by Studio

- Get all Consoles

- Get a Console by ID

- Get a Console by Manufacturer





