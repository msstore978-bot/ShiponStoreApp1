# Shipon Store Customer Management — Android V1 (Offline Foundation)

## GitHub Actions APK build — fixed

The earlier ZIP was missing `gradle/wrapper/gradle-wrapper.jar` (only
`gradle-wrapper.properties` was present), which makes `./gradlew` fail
immediately on any machine, including GitHub Actions, with an error like
`Could not find or load main class org.gradle.wrapper.GradleWrapperMain`.
There was also no `.github/workflows` file, so nothing was actually
building on push.

This version adds `.github/workflows/build-apk.yml`, which:
1. Installs JDK 17 and Gradle 8.7 directly on the runner.
2. Regenerates `gradle-wrapper.jar` before building, so a missing/corrupt
   wrapper jar can never break the build again.
3. Runs `./gradlew assembleDebug` and uploads the resulting APK as a
   workflow artifact (Actions tab → the workflow run → Artifacts →
   `app-debug-apk`).

It also adds `.gitattributes` to force LF line endings on `gradlew`
(a Windows checkout can otherwise silently turn it into a CRLF file,
which also breaks the Linux build runner).

To build locally in Android Studio, just open the project — Android
Studio will regenerate the wrapper jar itself on first sync as long as
you have an internet connection.


## 1. What was analyzed in your ZIP

`Customer_Management_System.zip` contained:

| File | Notes |
|---|---|
| `_Shipon_Store_Customer_Management_20154586.apk` | Built binary, **not source** |
| `_Shipon_Store_Customer_Management_20154586.aab` | Built bundle, **not source** |
| `Code.gs` (1279 lines) | Google Apps Script backend |
| `Index.html` (2184 lines) | Older frontend (no reminder-scheduler polish) |
| `Index-1.html` (2534 lines) | Current frontend (matches `Code.gs`'s reminder features) |

**There was no Android Studio source project in the ZIP** — only the compiled
APK/AAB. Per your own PART 11 instruction, an APK/AAB was **not** treated as
a source project; a brand-new Android Studio project was generated instead,
using `Code.gs` / `Index-1.html` purely as the reference for real business
logic and data shape.

### Important finding: Supplier / Product / Expense do not exist yet

Your prompt's overview and PART 3 mention Supplier, Product, SaleItem and
Expense functionality. Searching `Code.gs`, `Index.html`, and `Index-1.html`
found **zero** matches for "Supplier", no Product/line-item table, and no
Expense sheet or function. The real backend (`setupSheets()` in `Code.gs`)
only creates these sheets:

- `Dashboard_Settings`
- `Customers`
- `Sales`
- `Due_Add`
- `Due_Payments`
- `Legal_Notices`
- `Reminders`

**What I did about it:** Room entities were built to mirror those 7 real
sheets column-for-column (plus a `SyncQueue` outbox table for the future
sync engine). Supplier / Product / SaleItem / Expense were still created as
**empty placeholder tables** (per PART 3's "full business logic not needed
yet"), each with a doc comment flagging that they don't correspond to
anything in the current system and aren't referenced by any DAO logic or UI.
Nothing about the real Customers/Sales/Due/Reminders/Legal Notices/Settings
structure was changed, removed, or renamed.

## 2. Deliverables in this step (V1)

- Full Android Studio (Kotlin, Gradle Kotlin DSL) project structure
- `com.shiponstore.customermanagement`, min SDK 26 (Android 8+), target/compile SDK 34
- Room database `shipon_store.db` with entities: `Customer`, `Sale`,
  `DueTransaction` (Due_Add), `Payment` (Due_Payments), `Reminder`,
  `LegalNotice`, `DashboardSettings`, `SyncQueue`, and placeholder
  `Supplier` / `Product` / `SaleItem` / `Expense`
- DAOs for all of the above (full CRUD + search for Customer; basic CRUD
  scaffolding for the rest, as instructed)
- `CustomerRepository` (Repository layer) + `CustomerTestViewModel`
  (ViewModel layer) — Presentation → ViewModel → Repository → Room
- `MainActivity` (simple landing screen, links to the test screen —
  the existing dashboard/customer/due UI was intentionally **not**
  reproduced or redesigned in this step, per PART 5)
- `OfflineTestActivity` — the PART 6/7 developer test screen: add, list,
  search, edit, delete, and a live network-status banner so you can watch
  it keep working with the network off
- No network code anywhere; no WhatsApp/Alarm/Sync logic; existing
  `Code.gs` and both HTML files were only read, never modified

### Files created
Everything under `ShiponStoreApp/` is new (there was no prior source to
edit): Gradle files, manifest, all Kotlin sources under
`app/src/main/java/com/shiponstore/customermanagement/`, and all resources
under `app/src/main/res/`. See the zip for the full tree.

## 3. How to test PART 6 / PART 7 (offline)

1. Open the app → **Open Offline Database Test**.
2. Turn on Airplane Mode (banner switches to "Network: OFF").
3. Add a customer, search by name/shop/phone, tap the pencil icon to edit,
   tap the trash icon to delete — all while offline.
4. Close the app fully and reopen it — the list is still there (Room writes
   to disk immediately, it isn't an in-memory cache).

## 4. Building the project

1. Open the `ShiponStoreApp/` folder in Android Studio (Koala/2024.1 or
   newer recommended).
2. **One-time note:** this environment could not reach the internet, so
   `gradle/wrapper/gradle-wrapper.jar` (a binary file) is not included —
   only `gradlew`, `gradlew.bat`, and `gradle-wrapper.properties` are.
   Android Studio will prompt to **"Upgrade/Sync Gradle wrapper"** or you
   can run `gradle wrapper --gradle-version 8.7` once (with a local Gradle
   install) to regenerate the jar; after that `./gradlew` works normally.
3. Let Gradle sync (downloads AGP 8.5.2, Kotlin 1.9.24, Room 2.6.1, etc.
   — needs internet the first time).
4. **Build → Make Project**, then **Run ▶** on a device/emulator running
   Android 8.0 (API 26) or newer.

## 5. Explicitly out of scope for V1 (per your instructions)

Due/Supplier business logic, WhatsApp sending, AlarmManager-based
reminders, and the full Cloud Sync Engine are **not** implemented — only
the Local Room Database + Offline Foundation, as requested. These are
planned for V2 onward.

---

**V1 COMPLETED**
