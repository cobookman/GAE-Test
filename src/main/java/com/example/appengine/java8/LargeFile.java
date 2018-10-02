/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.java8;

// [START example]
import com.google.appengine.api.utils.SystemProperty;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "LargeFile", value = "/file")
public class LargeFile extends HttpServlet {
  private byte[] file;

  @Override
  public void init() throws ServletException {
    try {
      URL url = new URL("https://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_10mb.mp4");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      InputStream is = conn.getInputStream();
      this.file = IOUtils.toByteArray(is);
    } catch (Exception e) {
      // do nothing
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Properties properties = System.getProperties();

    response.setContentType("video/mp4");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    ServletOutputStream os = response.getOutputStream();
    os.write(this.file, 0, this.file.length);
  }

}
// [END example]
