# tai-pi-sprint-dashboard

The TAI Raspberry Pi sprint dashboard.

## update-sprint-data
Script for fetching sprint status from JIRA, and writing burndown status information to a database.

Note this has a few requirements:

* You are using JIRA agile board
* You have a custom property used for assigning points to tickets
* If you use subtasks, you want your burndown calculated from subtasks instead of stories. 

## How it works

If not using subtasks, burndown status is calculated from points and completion status from stories.

If using subtasks, burndown status is calculated from points and completion status from subtasks. This means the subtasks need to have points, or else the burndown status will be inaccurate.

This can handle a combination of stories with and without subtasks, using the above heuristics on a per-story basis.

### Setup

1. Copy `run.properties.SAMPLE` to `run.properties`
2. Open `run.properties` in an editor
3. Set `jira.host` to the JIRA hostname. Include the `/jira` path prefix, if necessary.
4. Set `jira.rapidViewId` to the rapid view ID for your agile board. (Used to fetch your agile board.)
5. Set `jira.issuePointsFieldName` to the custom field name corresponding to your points field.
6. Set `sprint.prefix` to a common String prefix for your sprints. (Used for identifying active sprint.)

## tai-sprint-dashboard-webapp ##
A web application which displays the data fetched from JIRA
