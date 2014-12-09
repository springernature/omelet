---
layout: post-index
title: All Posts
description: "An archive of posts."
comments: false
---

{% for post in site.releases %}
   <li>
   	<a href="{{post.url}}"</a>
   		{{content}}
   </li>
{% endfor %}