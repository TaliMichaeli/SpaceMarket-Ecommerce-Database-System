🌌 SpaceMarket – E-Commerce System
Submitted by: Tali Michaeli & Saar Avivi
Course: Introduction to Databases | 2025

🧾 Overview
SpaceMarket is a Java + PostgreSQL based e-commerce simulation system.
Users can register as Buyers, Sellers, or Admins to interact with the system:

Buyers: Browse products, add to cart, place orders, view order history, and leave reviews.

Sellers: Add new products, update product details (name, price, stock, etc.), and manage inventory.

Admins: Promote users, delete users (with cascading cleanup), and monitor the system.

🔧 Technologies Used
| Component   | Technology                    |
| ----------- | ----------------------------- |
| Programming | Java 17                       |
| DBMS        | PostgreSQL                    |
| Tools       | pgAdmin4, JDBC                |
| Design      | DAO, Factory, Facade Patterns |

🗂️ Project Structure
UserDAO, ProductDAO, OrderDAO, etc. – Data Access Objects (DAOs) for each table

EntityFactory – Creates users and products

Market – Main business logic

SpaceMarketControlFacade – Console UI and control logic

Main.java – Entry point

Diagram Reference - ERD with 5 core entities

🗄️ Database Schema
Core Tables
| Table              | Description                                    |
| ------------------ | ---------------------------------------------- |
| `users`            | User accounts and roles (BUYER, SELLER, ADMIN) |
| `product`          | Products listed by sellers                     |
| `shoppingcart`     | One cart per buyer                             |
| `shoppingcartitem` | Items added to carts                           |
| `orders`           | Orders placed by buyers                        |
| `orderitem`        | Items in each order                            |
| `review`           | Reviews left by buyers                         |

🧪 Features
Buyer
View available products or filter by category

Add products to cart

Place orders (updates stock automatically)

View past orders

Leave reviews for purchased products

Seller
Add new products

Update existing product attributes:

Name, price, category, stock, special packaging

View list of products added by the seller

Admin
Promote users to admin

Delete users:

BUYER: deletes cart, items, orders, reviews

SELLER: deletes their products

🔁 Trigger Logic
CREATE OR REPLACE FUNCTION prevent_product_deletion_if_in_cart()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM ShoppingCartItem
        WHERE pid = OLD.pid
    ) THEN
        RAISE EXCEPTION 'Cannot delete product %: it exists in a cart.', OLD.pid;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_product_deletion
BEFORE DELETE ON Product
FOR EACH ROW
EXECUTE FUNCTION prevent_product_deletion_if_in_cart();

🔁 Transaction Logic
In the DAO layer, we use transactional SQL blocks to handle product quantity in cart updates:
public void addItemToCart(int cartId, int pid, int quantity) {
    // Begin transaction
    // If product exists in cart, update quantity
    // Else, insert new row
    // Commit
}
This avoids duplicate rows and keeps cart data consistent.

✅ Summary
The SpaceMarket project is a real-world simulation of an online store, built with Java 17 and PostgreSQL using a structured layered architecture. It emphasizes:

Multi-role functionality

Full relational DB integration

Clean code separation (DAO, Business Logic, UI)

Database integrity via triggers and constraints

📝 Presented by: Tali Michaeli & Saar Avivi
📚 Database Course Project – 2025
