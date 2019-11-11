#!/usr/bin/env python3
import os
import sys
import json
import requests
import urllib.parse

def create_pull_request(url, title, head, auth = os.environ.get("GITHUB_TOKEN")):
    url = urllib.parse.urljoin("https://api.github.com/repos/", url)
    resp = requests.post(
        url = url,
        auth = (auth, ""),
        json = {
            "title": title,
            "base": "master",
            "head": head,
        }
    )
    if resp.status_code == 422:
        print("duplicate pull request")
    else:
        resp.raise_for_status()
        print(json.dumps(resp.json(), indent = 2))

def main(argv):
    if len(argv) < 4:
        print(f"Usage: {argv[0]} url title head [auth]")
        print("        url: <owner>/<repo>/pulls[/<numbers]")
        sys.exit(1)
    create_pull_request(*argv[1:5])

if __name__ == "__main__":
    main(sys.argv)