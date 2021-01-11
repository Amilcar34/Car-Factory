#This line is but use the image base from websphere than any created. The :latest is but used the lastet version than any up.
FROM ibmcom/websphere-liberty:latest

# Add my app and config
COPY --chown=1001:0  cfs-backend-*.jar /config/dropins/
COPY --chown=1001:0  server.xml /config/

# Add interim fixes (optional)
COPY --chown=1001:0  interim-fixes /opt/ibm/fixes/

# Default setting for the verbose option
ARG VERBOSE=false

# This script will add the requested XML snippets, grow image to be fit-for-purpose and apply interim fixes
RUN configure.sh