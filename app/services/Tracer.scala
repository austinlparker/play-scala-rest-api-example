
package services

import io.opentracing.util.GlobalTracer
import javax.inject._
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class Tracer @Inject() (lifecycle: ApplicationLifecycle) {
  val tracer = new com.lightstep.tracer.jre.JRETracer(
    new com.lightstep.tracer.shared.Options.OptionsBuilder()
      .withAccessToken("TEST_TOKEN")
      .withCollectorHost("localhost")
      .withCollectorPort(9996)
      .withCollectorProtocol("http")
      .build()
  )
  GlobalTracer.register(tracer)

  lifecycle.addStopHook { () =>
    tracer.flush(30000)
    Future.successful(())
  }
}