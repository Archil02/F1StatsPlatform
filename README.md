# F1 Stats Platform

F1 სტატისტიკისა და შეტყობინებების პლატფორმა — Java + Spring Boot + PostgreSQL.

## გაშვება

### მოთხოვნები
- Java 21+
- PostgreSQL
- IntelliJ IDEA

### ნაბიჯები

**1. Database შექმენი pgAdmin-ში:**
```sql
CREATE DATABASE f1platform;
```

**2. Environment variables დააყენე**

პროექტში არის `.env.example` ფაილი ცარიელი ველებით — გადაარქვი `.env`-ად
(ან უბრალოდ დააყენე environment variables ოპერაციულ სისტემაში / IntelliJ-ის
Run Configuration-ში) და შეავსე საკუთარი მონაცემებით:

| Variable      | აღწერა                                    |
|----------------|--------------------------------------------|
| `DB_USERNAME`  | PostgreSQL username                         |
| `DB_PASSWORD`  | PostgreSQL password                         |
| `MAIL_USERNAME`| Gmail მისამართი საიდანაც გაიგზავნება ემაილი |
| `MAIL_PASSWORD`| Gmail **App Password** (არა ჩვეულებრივი პაროლი) |
| `JWT_SECRET`   | ნებისმიერი გრძელი რენდომ სტრინგი             |

**Gmail App Password-ის გენერაცია:** Google Account → Security →
2-Step Verification → App Passwords.

**IntelliJ-ში დაყენების გზა:**
Run → Edit Configurations → Environment Variables →
`DB_USERNAME=...;DB_PASSWORD=...;MAIL_USERNAME=...;MAIL_PASSWORD=...;JWT_SECRET=...`

> ⚠️ Email ფუნქციონალი არასავალდებულოა — თუ `MAIL_USERNAME`/`MAIL_PASSWORD`
> არ შეივსება, აპლიკაცია მაინც გაეშვება, უბრალოდ ემაილი არ გამოიგზავნება
> (ფუნქციონალის დანარჩენ ნაწილზე გავლენას არ ახდენს).

**3. პროექტი გახსენი IntelliJ-ში და გაუშვი `F1StatsPlatformApplication`**

სერვერი ავტომატურად:
- შექმნის ყველა ცხრილს
- ჩამოტვირთავს მიმდინარე სეზონის F1 მონაცემებს Jolpica API-დან
- შექმნის Admin მომხმარებელს

**4. Browser-ში გახსენი:**
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

- **Schedule** — სეზონის სრული კალენდარი (public)
- **Race Results** — ჩატარებული რბოლების შედეგები (login საჭიროა)
- **Drivers** — მძღოლების სტანდინგი ქულებით (login საჭიროა)
- **Email ნოტიფიკაციები** — რბოლის დაწყება/დასრულებისას
  ემაილი ეგზავნება მხოლოდ იმ მომხმარებლებს, ვინც ეს subscription ჩართული
  აქვს. რეგისტრაციისას default-ად ჩართულია, ნებისმიერ დროს nav-ის 🔔 ღილაკით
  შეიძლება ჩართვა/გამორთვა.

---

## ტექნოლოგიები

- Java 21 · Spring Boot 3.5 · Spring Security (JWT)
- PostgreSQL · Spring Data JPA · Hibernate
- Jolpica F1 API · OpenF1 API
- Gmail SMTP · Spring Mail