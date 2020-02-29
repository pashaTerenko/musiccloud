var Playlist;
var currentPos = 0;
var speedyyy=1;
function playB(data, id) {
    document.getElementById("audiotrack").setAttribute("src", "/get?uuid=" + data[id].uuid);


    document.getElementById("trackName").innerText = data[id].name;
    document.getElementById("Totaltime").innerText = data[id].aLong;
    setText(playButton, "Pause");
    audioTrack.play();

    Playlist = data;
    currentPos = id;


}

function player() {
    if (audioTrack.paused) {
        setText(this, "Pause");

        audioTrack.play();
    } else {
        setText(this, "Play");
        audioTrack.pause();
    }
}
function setText(el, text) {
    el.innerHTML = text;
}

function finish() {
    audioTrack.currentTime = 0;
    setText(playButton, "Play");
    playB(Playlist, currentPos + 1 > Playlist.length ? 0 : currentPos + 1);
}

function updatePlayhead() {
    playhead.value = audioTrack.currentTime;
    var s = parseInt(audioTrack.currentTime % 60);
    var m = parseInt((audioTrack.currentTime / 60) % 60);
    s = (s >= 10) ? s : "0" + s;
    m = (m >= 10) ? m : "0" + m;
    playbacktime.innerHTML = m + ':' + s;

}

function volumizer() {
    if (audioTrack.volume == 0) {
        setText(muteButton, "volume");
    } else {
        setText(muteButton, "volumehigh");
    }
}

function muter() {
    if (audioTrack.volume == 0) {
        audioTrack.volume = restoreValue;
        volumeSlider.value = restoreValue;
    } else {
        audioTrack.volume = 0;
        restoreValue = volumeSlider.value;
        volumeSlider.value = 0;
    }
}

function setAttributes(el, attrs) {
    for (var key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
}
var restoreValSP=10;
function changeSpeed() {
    if (audioTrack.playbackRate == 0) {

        audioTrack.volume = restoreValSP;
        SpeedSlider.playbackRate = restoreValSP;
    } else {
        audioTrack.playbackRate = 0;
        restoreValSP = volumeSlider.playbackRate;
        SpeedSlider.playbackRate = 0;
    }
}
var audioPlayer = document.getElementById("audioplayer"),
    fader = document.getElementById("fader"),
    fader = document.getElementById("faderSpeed"),
    playback = document.getElementById("playback"),
    audioTrack = document.getElementById("audiotrack"),
    playbackTime = document.getElementById("playbacktime"),
    playButton = document.createElement("button"),
    muteButton = document.createElement("button"),
    playhead = document.createElement("progress")
trackName = document.createElement("div")
volumeSlider = document.createElement("input"),
    SpeedSlider= document.createElement("input")
    muteButton = document.createElement("button");
setText(playButton, "Play");
setText(muteButton, "volumehigh");
setAttributes(playButton, {"type": "button", "class": "ss-icon"});
setAttributes(muteButton, {"type": "button", "class": "ss-icon"});
muteButton.style.display = "inline-block";
muteButton.style.margin = "0 auto";
setAttributes(volumeSlider, {
    "type": "range",
    "min": "0",
    "max": "1",
    "step": "any",
    "value": "1",
    "orient": "horizontal",
    "id": "volumeSlider"

});
setAttributes(SpeedSlider, {
    "type": "range",
    "min": "0",
    "max": "10",
    "step": "any",
    "value": "0.01",
    "orient": "horizontal",
    "id": "SpeedSlider",
    "onChange":"changeSpeed()"
});
var duration = audioTrack.duration;
setAttributes(playhead, {"min": "0", "max": "100", "value": "0", "id": "playhead"});
fader.appendChild(muteButton);
fader.appendChild(volumeSlider);
fader.appendChild(trackName)
playback.appendChild(playButton);
playback.appendChild(playhead);
audioTrack.removeAttribute("controls");

muteButton.addEventListener("click", muter, false);
volumeSlider.addEventListener("input", function () {
    audioTrack.volume = volumeSlider.value;
}, false);
audioTrack.addEventListener('volumechange', volumizer, false);
audioTrack.addEventListener('playing', function () {
    playhead.max = audioTrack.duration;
}, false);
audioTrack.addEventListener('timeupdate', updatePlayhead, false);
audioTrack.addEventListener('ended', finish, false);