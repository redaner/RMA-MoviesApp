package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GlumciAktivnost extends AppCompatActivity {

    public static boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glumci_aktivnost);
        final Button dugme_glumci = (Button)findViewById(R.id.buttonGlumci);
        final Button dugme_reziseri = (Button)findViewById(R.id.buttonReziseri);
        final Button dugme_zanrovi = (Button)findViewById(R.id.buttonZanrovi);
        final ListView lista_glumci = (ListView)findViewById(R.id.listaGlumci);
        ArrayList<Glumac> glumci = new ArrayList<>();
        ArrayList<Reziser> reziseri = new ArrayList<>();
        ArrayList<Zanr> zanrovi = new ArrayList<>();

        if (flag) {
            Intent i = getIntent();
            glumci = i.getParcelableArrayListExtra("glumci");
            reziseri = i.getParcelableArrayListExtra("reziseri");
            zanrovi = i.getParcelableArrayListExtra("zanrovi");
        }
        else {
            flag = true;
            glumci = new ArrayList<Glumac>();
            glumci.add(new Glumac("Leonardo", "DiCaprio", 1974, -1, "Leonardo Wilhelm DiCaprio is a renowned American actor and producer known for his good looks and exceptional acting skills. Marking his entry through television in 1991 with ‘Santa Barbara’, he went on to become an international star. He ventured into the film world through a horror flick ‘Critters 3’ and continued with many more like ‘This Boy’s Life’, ‘Titanic’, ‘The Man in the Iron Mask’ and others. His participation in dramas like ‘Romeo + Juliet’, ‘The Basket ball Dairies’ and ‘Catch Me If You Can’ brought him tremendous acclaim. It was ‘Titanic’ that served as a milestone in turning around his overall image as an actor. He received his first Golden Globe Award post ‘Titanic’. He has touched upon various genres of cinema ranging from romance, historical and period drama, thriller and even science fiction. He has received some of the most coveted honors such as the Golden Globe Awards for Best Actor in a Drama, Musical or Comedy for his performance in the films ‘The Aviator’ and ‘The Wolf of Wall Street’, and the Academy Award for Best Actor for the movie ‘The Revenant’. Apart from being a producer and an actor, he is also a philanthropist. His concern for the society and the environment is clearly evident from the donations he makes towards wildlife and environment conservational groups.\n" +
                    "Read more at http://www.thefamouspeople.com/profiles/leonardo-wilhelm-dicaprio-2135.php#3jbdxgJVjgV4KsTP.99", "leodicaprio", "Los Angeles", "M", "http://www.imdb.com/name/nm0000138/"));
            glumci.add(new Glumac("Jack", "Nicholson", 1937, -1, "John \"Jack\" Joseph Nicholson (born April 22, 1937) is an American actor, director, producer, and screenwriter, internationally renowned for his often dark-themed portrayals of neurotic characters. \n" +
                    "\n" +
                    "Nicholson has been nominated for an Academy Award 12 times, and has won three: two for Best Actor and one for Best Supporting Actor. He is tied with Walter Brennan for most acting wins by a male actor (three), and second to Katharine Hepburn for most acting wins overall (four). He is also one of only two actors nominated for an Academy Award for acting (either lead or supporting) in every decade since the 1960s; the other is Michael Caine. He has won seven Golden Globe Awards, and received a Kennedy Center Honor in 2001. In 1994, he became one of the youngest actors to be awarded the American Film Institute's Life Achievement Award. Notable films that he starred in include, in chronological order, Easy Rider, Five Easy Pieces, Chinatown, One Flew Over the Cuckoo's Nest, The Passenger, The Shining, Terms of Endearment, Batman, A Few Good Men, As Good as It Gets, About Schmidt, Something's Gotta Give, and The Departed. ", "jacknicholson", "New Jersey", "M", "http://www.imdb.com/name/nm0000197/"));
            glumci.add(new Glumac("Brad", "Pitt", 1963, -1, "Early Life\n" +
                    "\n" +
                    "Actor Brad Pitt was born William Bradley Pitt on December 18, 1963, in Shawnee, Oklahoma, the eldest of three children in a devoutly Southern Baptist family, and grew up in Springfield, Missouri. His father, Bill Pitt, owned a trucking company, and his mother, Jane Pitt, was a family counselor. Pitt originally aspired to be an advertising art director, studying journalism at the University of Missouri.\n" +
                    "\n" +
                    "However, the young college student had other, quiet aspirations that were the product of a childhood love of movies. His dreams finally seemed tangible his last semester at university when he realized, \"I can leave.\" On a whim, Pitt dropped out of college, packed up his Datsun and headed West to pursue an acting career in Los Angeles, just two credits shy of a college degree.\n" +
                    "\n" +
                    "Pitt told his parents he intended to enroll in the Art Center College of Design in Pasadena, but instead spent the next several months driving a limousine—chauffeuring strippers from one bachelor party to the next, delivering refrigerators and trying to break into the L.A. acting scene. He joined an acting class and, shortly after, accompanied a classmate as her scene partner on an audition with an agent. In a twist of fate, the agent signed Pitt instead of his classmate. After weathering only seven months in Los Angeles, Pitt had secured an agent and regular acting work.", "bradpitt", "Shawnee", "M", "http://www.imdb.com/name/nm0000093/"));
            glumci.add(new Glumac("Tom", "Hanks", 1956, -1, "Synopsis\n" +
                    "\n" +
                    "Born on July 9, 1956, in Concord, California, actor Tom Hanks began performing with the Great Lakes Shakespeare Festival in 1977, later moving to New York City. He starred in the television sitcom Bosom Buddies, but became far more known when he starred in the Ron Howard film Splash. He went on to star in many more popular and acclaimed movies, including Big, Forrest Gump and Cast Away, and is now arguably one of the most powerful and well-respected actors in Hollywood.", "tomhanks", "Concord", "M", "http://www.imdb.com/name/nm0000158/"));
            glumci.add(new Glumac("Uma", "Thurman", 1970, -1, "Uma Karuna Thurman was born in Boston, Massachusetts, into a highly unorthodox and Eurocentric family. She is the daughter of Nena Thurman (née Birgitte Caroline von Schlebrügge), a fashion model and socialite who now runs a mountain retreat, and of Robert Thurman (Robert Alexander Farrar Thurman), a professor and academic who is one of the nation's foremost Buddhist scholars. Uma's mother was born in Mexico City, Mexico, to a German father and a Swedish mother (who herself was of Swedish, Danish, and German descent). Uma's father, a New Yorker, has English, Scots-Irish, Scottish, and German ancestry. Uma grew up in Amherst, Massachusetts, where her father worked at Amherst College.", "umathurman", "Boston", "F", "http://www.imdb.com/name/nm0000235/"));
            glumci.add(new Glumac("Scarlett", "Johansson", 1984, -1, "Synopsis\n" +
                    "\n" +
                    "Scarlett Johansson was born in New York City on November 22, 1984. She began acting as a child, and her role in the movie The Horse Whisperer brought her critical acclaim at age 13. Her subsequent successes include Lost in Translation, Girl with a Pearl Earring, The Nanny Diaries, Vicky Cristina Barcelona and the mega-hit The Avengers. Johansson also appeared in 2012's Hitchcock, a biopic of famed horror director Alfred Hitchcock. Exploring her love of music, Johansson released her first album in 2008 with musician Pete Yorn. Her recent films include Captain America: The Winter Soldier (2014) and Avengers: Age of Ultron (2015).", "scarlettjohansson", "New York City", "F", "http://www.imdb.com/name/nm0424060/"));

            glumci.add(new Glumac("Audrey", "Hepburn", 1929, 1993, "Audrey Hepburn was born Audrey Kathleen Hepburn-Ruston on May 4, 1929 in Brussels, Belgium. She was a blue-blood and a cosmopolitan from birth. Her mother, Ella van Heemstra, was a Dutch baroness; Audrey's father, Joseph Victor Anthony Hepburn-Ruston, was born in Úzice, Bohemia, of English and Austrian descent, and worked in business.\n" +
                    "\n" +
                    "After her parents divorced, Audrey went to London with her mother where she went to a private girls school. Later, when her mother moved back to the Netherlands, she attended private schools as well. While she vacationed with her mother in Arnhem, Netherlands, Hitler's army took over the town. It was here that she fell on hard times during the Nazi occupation. Audrey suffered from depression and malnutrition.","audreyhepburn", "Ixelles" , "F", "http://www.imdb.com/name/nm0000030/" ));

            reziseri = new ArrayList<>();
            reziseri.add(new Reziser("Stanley", "Kubrick"));
            reziseri.add(new Reziser("Denis", "Villeneuve"));
            reziseri.add(new Reziser("Damien", "Chazelle"));
            reziseri.add(new Reziser("Francis", "Coppola"));
            reziseri.add(new Reziser("Kathryn", "Bigelow"));

            zanrovi = new ArrayList<>();
            zanrovi.add(new Zanr("Akcija", "action"));
            zanrovi.add(new Zanr("Horor", "horror"));
            zanrovi.add(new Zanr("Sci-Fi", "scifi"));
            zanrovi.add(new Zanr("Fantasy", "fantasy"));
            zanrovi.add(new Zanr("Komedija", "comedy"));
        }
        final ArrayList<Glumac> gl = glumci;
        final ArrayList<Reziser> re = reziseri;
        final ArrayList<Zanr> za = zanrovi;
        final GlumacAdapter adapter;
        adapter = new GlumacAdapter(this, R.layout.glumac_u_listi, glumci, getResources());
        lista_glumci.setAdapter(adapter);

        lista_glumci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(GlumciAktivnost.this, BiografijaAktivnost.class);
                Glumac g = gl.get(position);
                myIntent.putExtra("glumac", g);
                GlumciAktivnost.this.startActivity(myIntent);
            }
        });

        dugme_zanrovi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GlumciAktivnost.this, ZanrAktivnost.class);
                i.putExtra("glumci", gl);
                i.putExtra("reziseri", re);
                i.putExtra("zanrovi", za);
                GlumciAktivnost.this.startActivity(i);
            }
        });

        dugme_reziseri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GlumciAktivnost.this, ReziseriAktivnost.class);
                i.putExtra("glumci", gl);
                i.putExtra("reziseri", re);
                i.putExtra("zanrovi", za);
                GlumciAktivnost.this.startActivity(i);
            }
        });
    }
}
