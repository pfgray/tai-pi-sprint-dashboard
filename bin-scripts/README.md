# bin-scripts/

These are scripts meant to be placed in `/home/pi/bin`

## update-sprint-data

Update sprint data from JIRA, file `/home/pi/bin/run.properties` must exist.

## update-seven-segs

Update seven segment displays with scores.

Usage:
```bash
# sets green display to 2, red display to 3
update-seven-segs 2 3

# sets green display to 4.5, red display to 8
update-seven-segs 4.5 8

# clears displays
update-seven-segs '' ''
```

## refresh-browser

Refreshes the display of the sprint board temporarily disabling the the screensaver.
