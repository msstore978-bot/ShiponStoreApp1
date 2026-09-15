package com.shiponstore.customermanagement.data.local.entity

/**
 * Sync status for every offline-first entity in this app (PART 3 / PART 4).
 *
 * PENDING -> created/edited locally, not yet pushed to Google Sheets.
 * SYNCED  -> matches what is on the server (Apps Script / Google Sheets).
 * FAILED  -> a sync attempt was made and failed (kept locally, retried later).
 *
 * Nothing in V1 sets anything other than PENDING - the Sync Engine that
 * moves rows through SYNCED / FAILED is a later step, not part of this
 * Android Offline Foundation.
 */
enum class SyncStatus {
    PENDING,
    SYNCED,
    FAILED
}
