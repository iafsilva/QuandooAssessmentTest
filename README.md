# Quandoo Candidate Task

## The Task

We need an application to make reservations in a restaurant

## Workflow

The waiter opens the app, chooses a customer from a list, the app navigates him to a table choosing screen.
The waiter chooses the table and the app highlights the chosen table.
Every 15 minutes all reservations have to be removed even if the application is closed.

## Requirements

1. Tables have to be represented as cells on grid
2. Available and unavailable tables have to be easily recognized
3. The app has to also work offline
4. Search option for customers would be a plus
5. Any unspecified details are left to your imagination

## Implementation

* Android Native Code with MVC architecture

## Dependencies

1. GSON

    To parse the JSON received from the API Endpoint

2. Retrofit

    To create the HTTP client for the API Endpoint

3. Android-Job

    To create and schedule the required Job, in any API level, without the need for Google Play Store.

## Screenshots

<img src="https://github.com/iafsilva/QuandooAssessmentTest/blob/master/screenshots/screen_customers.png" width="300">

<img src="https://github.com/iafsilva/QuandooAssessmentTest/blob/master/screenshots/screen_customers_search.png" width="300">

<img src="https://github.com/iafsilva/QuandooAssessmentTest/blob/master/screenshots/screen_table.png" width="300">