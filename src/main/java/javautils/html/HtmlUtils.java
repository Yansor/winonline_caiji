 package javautils.html;

 import org.htmlparser.Parser;
 import org.htmlparser.filters.CssSelectorNodeFilter;
 import org.htmlparser.filters.TagNameFilter;
 import org.htmlparser.util.NodeList;
 import org.htmlparser.util.ParserException;



 public class HtmlUtils
 {
   public static NodeList FilterCss(String html, String css, String charset)
     throws ParserException
   {
     Parser parser = Parser.createParser(html, charset);
     CssSelectorNodeFilter filter = new CssSelectorNodeFilter(css);
     NodeList nodeList = parser.extractAllNodesThatMatch(filter);
     return nodeList;
   }

   public static NodeList FilterTag(String html, String tag, String charset) throws ParserException {
     Parser parser = Parser.createParser(html, charset);
     TagNameFilter filter = new TagNameFilter(tag);
     NodeList nodeList = parser.extractAllNodesThatMatch(filter);
     return nodeList;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/html/HtmlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */