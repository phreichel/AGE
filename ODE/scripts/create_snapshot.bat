python prepend_headers.py
CD ..
git commit -am "Generating Snapshot"
git archive -o .\delivery\ODE.zip HEAD
git reset --hard HEAD
