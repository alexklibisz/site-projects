import csv, gzip, json, sys

with gzip.open(sys.argv[1]) as g:
  w = csv.DictWriter(sys.stdout, fieldnames=["review_id","reviewer_id","reviewer_name","asin","summary"])
  w.writeheader()
  for i,l in enumerate(g):
    d = json.loads(l)
    w.writerow(dict(
      review_id=i,
      reviewer_id=d.get('reviewerID', ''),
      reviewer_name=d.get('reviewerName', ''),
      asin=d.get('asin', ''),
      summary=d.get('summary', '')
    ))