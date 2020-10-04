# Questions for PPL(A), PL: Pytania_Egzaminacyjne_PPL(A)

#### Android app, which purpose is to make studying to aviation exam (PPL- Private Pilot License) easier by exposing questions and answers in convenient way. There are 3 modes: learni, test and exam simulation. 

#### App is published on Google Play [https://play.google.com/store/apps/details?id=com.aplikacji.pytaniaegzaminacyjneppla]

App is witten in Java. Also used:
- Shared Preferences
- Admob Moblie Ads


#### 1. Opening acivity

<img src="/Screenshots/Scr_1_apk.png" width="200">

Here user decides if he wants to learn, test himself/herself or simulate exam.

#### 2. Choosing subject

<img src="/Screenshots/Scr_3_apk.png" width="200">

There is a list of subjetcs, so it enables user to choose questions of one particular subject. However, in learn mode and in test mode there is also an option to choose all subjects - and get questions from all subjects at once. In exam mode this functionality wouldn't make any sense.

#### 3. Learn mode

<img src="/Screenshots/Scr_4_apk.png" width="200">

Question is exposed with one, correct answer. By entering number in edittext and presing "Go!" button user can jump to specified question immediately. If there isn't any number toast will appear with message to enter number firstly; if entered number is out of range there also will be a toast - it won't crash app.

#### 4. Test mode

<img src="/Screenshots/Scr_2_apk.png" width="200">

<img src="/Screenshots/Scr_5_apk.png" width="200">

Question is exposed with 4 answers (only 1 correct). When user taps answer it changes color to red (if it's wrong) or green (if it's correct). Answers are shuffled every time user goes to next/previous question or closes app. This forces to learn correct answer instead of correct order :smile:. There is option to jump to specified question - same as in learn mode.

#### 5. Exam simulation mode

<img src="/Screenshots/Scr_7_apk.png" width="200">

Few random questions from subject are picked with 4 answers. Number of questions is same as on real exam in Civil Aviation Department. When user taps answer it changes color to blue. User can pick only one answer, but it is possible to change it - other will appear with blue background, previous answer will change background to default. Answers are remembered so user can go back to questions which he has already given answer to and change them. By pressing "check" button user goes to results view.

#### 6. Exam results view

<img src="/Screenshots/Scr_6_apk.png" width="200">

Here are displayed user's results from exam. There is a number of correct answers and also a percentage (correct answers divided by total number of questions). If score is above 75% (needed to pass exam) text is in green color with huge "Bravo!" on screen. Otherwise text is in red color, telling to try again.
