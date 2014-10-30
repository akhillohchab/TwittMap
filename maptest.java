import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.mapsengine.MapsEngine;
import com.google.api.services.mapsengine.MapsEngineRequestInitializer;
import com.google.api.services.mapsengine.model.Feature;
import com.google.api.services.mapsengine.model.FeaturesListResponse;
import com.google.api.services.mapsengine.model.GeoJsonMultiPolygon;
import com.google.api.services.mapsengine.model.GeoJsonPolygon;
import com.google.maps.clients.BackOffWhenRateLimitedRequestInitializer;
import com.google.maps.clients.mapsengine.Security;
import com.google.maps.clients.mapsengine.geojson.MultiPolygon;
import com.google.maps.clients.mapsengine.geojson.Polygon;

import java.util.Collections;
import java.util.List;

/** Java "Hello, world!" example using the client library and supplementary wrapper. */
class maptest {

  static final String SAMPLE_TABLE_ID = "12421761926155747447-14043129889721455791";
  static final String PUBLIC_API_KEY = "YOUR_API_KEY_HERE";

  public static void main(String[] args) throws Exception {
    HttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new GsonFactory();

    // This request initializer will ensure the API key is sent with every HTTP request.
    MapsEngineRequestInitializer apiKeyInitializer =
        new MapsEngineRequestInitializer(PUBLIC_API_KEY);

    // This request initializer will automatically retry any request that is denied by the server
    // due to rate limiting / quota throttling.  It will back off exponentially with each denial.
    HttpRequestInitializer exponentialBackOff = new BackOffWhenRateLimitedRequestInitializer();

    MapsEngine engine = new MapsEngine.Builder(transport, jsonFactory, exponentialBackOff)
        .setApplicationName("Google-MapsEngineSample/1.0")
        .setMapsEngineRequestInitializer(apiKeyInitializer)
        .build();

    List<Polygon> hawaiianIslands = readStateShapes(engine, "Hawaii");
    Polygon maui = hawaiianIslands.get(6);
    System.out.println("Found Maui!");
  }

  private static List<Polygon> readStateShapes(MapsEngine engine, String state) throws Exception {
    // Query using an "unsafe" external parameter, correctly escaped and quoted.
    FeaturesListResponse response = engine.tables().features().list(SAMPLE_TABLE_ID)
        .setVersion("published")
        .setWhere(String.format("NAME = %s", Security.escapeAndQuoteString(state)))
        .execute();

    // Grab the list of features (rows) returned by the API.
    List<Feature> features = response.getFeatures();

    // For this example, we should only ever get one row returned.
    if (features.size() == 1) {
      Feature feature = features.get(0);
      // Convert from the Maps Engine shape representations to List<Polygon>.
      if (feature.getGeometry() instanceof GeoJsonMultiPolygon) {
        return new MultiPolygon(feature).getPolygons();
      } else if (feature.getGeometry() instanceof GeoJsonPolygon) {
        return Collections.singletonList(new Polygon(feature));
      } else {
        throw new IllegalStateException("States should be composed of one or more polygons.");
      }
    } else {
      throw new IllegalArgumentException("Did not find exactly one state matching the query.");
    }
  }
}