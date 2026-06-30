# F1 Stats Platform

F1 სტატისტიკისა და შეტყობინებების პლატფორმა — Java + Spring Boot + PostgreSQL.

## გაშვება

### მოთხოვნები
- Java 21+
- PostgreSQL
- IntelliJ IDEA

### ნაბიჯები

## კონფიგურაცია

თუ PostgreSQL-ის username ან password განსხვავებულია,
დააყენე environment variables:

| Variable | აღწერა | Default |
|----------|--------|---------|
| DB_USERNAME | PostgreSQL username | postgres |
| DB_PASSWORD | PostgreSQL password | postgres |

**IntelliJ-ში დაყენება:**
Run → Edit Configurations → Environment Variables:
DB_USERNAME=შენი_იუზერი
DB_PASSWORD=შენი_პაროლი


**1. Database შექმენი pgAdmin-ში:**
```sql
CREATE DATABASE f1platform;
```

**2. პროექტი გახსენი IntelliJ-ში და გაუშვი `F1StatsPlatformApplication`**

სერვერი ავტომატურად:
- შექმნის ყველა ცხრილს
- ჩამოტვირთავს 2026 სეზონის F1 მონაცემებს
- შექმნის Admin მომხმარებელს

**3. Browser-ში გახსენი:**
```
http://localhost:8090
```

---

## მომხმარებლები

| Role  | Email             | Password  |
|-------|-------------------|-----------|
| Admin | admin@f1stats.com | admin123  |

რეგისტრაციაც შეგიძლია — `http://localhost:8090/register.html`

---

## ფუნქციონალი

- 2026 სეზონის სრული კალენდარი (public)
- ჩატარებული რბოლების შედეგები (login საჭიროა)
- მძღოლების სტანდინგი ქულებით (login საჭიროა)
- რბოლის დაწყება/დასრულებისას ყველა მომხმარებელს ეგზავნება

---

## ტექნოლოგიები

- Java 21 · Spring Boot 3.5 · Spring Security (JWT)
- PostgreSQL · Spring Data JPA · Hibernate
- Jolpica F1 API · OpenF1 API
- Gmail SMTP · Spring Mail
