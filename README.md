# Fetch Job Servuce 
This service exposes just one GET mapping which in input takes user linkedin session cookie and csrf-token , based on above parameter it pulls all user jobs 
which are in SAVED , APPLIED , ARCHIVED , IN PROGRESS STATUS and returns json response pointed below .

{
  "saved": {
    "jobs": [
      {
        "role": "Java Backend Lead Software Engineer",
        "company": "JPMorganChase",
        "location": "Glasgow (On-site)",
        "appliedOn": "Posted 1w ago"
      },
      {
        "role": "Senior Associate, Full-Stack Engineer",
        "company": "BNY",
        "location": "Greater Manchester (On-site)",
        "appliedOn": "Be an early applicant"
      }
    ]
  },
  "applied": {
    "jobs": [
      {
        "role": "Senior Software Engineer",
        "company": "Roku",
        "location": "Cambridge (On-site)",
        "appliedOn": "Applied 26d ago"
      }
    ]
  },
  "inProgress": {
    "jobs": []
  },
  "archived": {
    "jobs": [
      {
        "role": "Java Lead Software Engineer",
        "company": "JPMorganChase",
 


