
import io.opentracing.play._
import io.opentracing.util.GlobalTracer
import play.http.DefaultHttpFilters

class Filters extends DefaultHttpFilters(
  new TracingFilter(GlobalTracer.get(),
    Seq(
      new HttpVersionTagger,
      new TagsSpanTagger(_ => true),
      new ContentTagger,
      new StandardSpanTagger()
    ))
)