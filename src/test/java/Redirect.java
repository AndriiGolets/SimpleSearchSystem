/**
 * Created by andrii on 1/3/17.
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class Redirect {

        public static void main(String[] args) {

            try {

                String url = "http://www.facebook.com";

                // String url = "http://www.ukr.net/ua/?go=https%3A%2F%2Fan.yandex.ru%2Fcount%2F7czBiFCvXoW40000ZhHClOO5XPwE9PK1cm5kGxS28uYqDVoA1ecaDxG1cAFVlRwU0fskrxeGklmbEx5aHD561QPpYhDDL34BtBWFPMGClR1m2Ji8gW6bfuRH2xoaDD04tG7Ua2JqaRmpiTiDZxm_rEK2dx1bloe3auy8cM92Z9GZBRQGXZsra4Kpe9NMFw-K8ospc898j915CzcNUq3Qa3v9b9QQ3wUTlm6ehUFmKwmEhlcwG5Taf0Ak3x2o3Xsx1R41igfJ00AvhjUw4BlgDaRViZaZlC7__________m_5Zm_C1SswJ5Gn2yw_36La3DC4xW7RzNocEsyEI3u2tuy8wWBbaDGmxOGzyPTp1VN2tR1EbBhssyp4z3LJL7eY%3Ftest-tag%3D165476558707713%26\" target=\"_blank\" class=\"yap-title-block__text yap-title-text yap-title-font-size yap-title-color yap-title-hover-color\" _xc_=\"https://an.yandex.ru/count/7czBiFCvXoW40000ZhHClOO5XPwE9PK1cm5kGxS28uYqDVoA1ecaDxG1cAFVlRwU0fskrxeGklmbEx5aHD561QPpYhDDL34BtBWFPMGClR1m2Ji8gW6bfuRH2xoaDD04tG7Ua2JqaRmpiTiDZxm_rEK2dx1bloe3auy8cM92Z9GZBRQGXZsra4Kpe9NMFw-K8ospc898j915CzcNUq3Qa3v9b9QQ3wUTlm6ehUFmKwmEhlcwG5Taf0Ak3x2o3Xsx1R41igfJ00AvhjUw4BlgDaRViZaZlC7__________m_5Zm_C1SswJ5Gn2yw_36La3DC4xW7RzNocEsyEI3u2tuy8wWBbaDGmxOGzyPTp1VN2tR1EbBhssyp4z3LJL7eY?test-tag=165476558707713&amp";

                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setReadTimeout(5000);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                conn.addRequestProperty("Referer", "google.com");

                System.out.println("Request URL ... " + url);

                boolean redirect = false;

                // normally, 3xx is redirect
                int status = conn.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }

                System.out.println("Response Code ... " + status);

                if (redirect) {

                    // get redirect url from "location" header field
                    String newUrl = conn.getHeaderField("Location");

                    // get the cookie if need, for login
                    String cookies = conn.getHeaderField("Set-Cookie");

                    // open the new connnection again
                    conn = (HttpURLConnection) new URL(newUrl).openConnection();
                    conn.setRequestProperty("Cookie", cookies);
                    conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                    conn.addRequestProperty("User-Agent", "Mozilla");
                    conn.addRequestProperty("Referer", "google.com");

                    System.out.println("Redirect to URL : " + newUrl);

                } else {
                    System.out.printf("No redirect detected \n");
                }

                System.out.println("Done");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


}
