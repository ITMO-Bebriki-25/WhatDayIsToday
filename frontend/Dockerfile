FROM nginx:alpine

# Create directory for the app
WORKDIR /usr/share/nginx/html

# Copy all frontend files
COPY . .

# Remove default nginx config
RUN rm /etc/nginx/conf.d/default.conf

# Copy our custom nginx config
COPY nginx.conf /etc/nginx/conf.d/

EXPOSE 8000

CMD ["nginx", "-g", "daemon off;"]
