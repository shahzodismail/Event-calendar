package com.sportradar.event_calendar.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportradar.event_calendar.model.Event;
import com.sportradar.event_calendar.model.Sport;
import com.sportradar.event_calendar.model.Team;
import com.sportradar.event_calendar.repository.EventRepository;
import com.sportradar.event_calendar.repository.SportRepository;
import com.sportradar.event_calendar.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        JsonNode rootNode = mapper.readTree(inputStream);
        JsonNode dataNode = rootNode.get("data");

        if (dataNode.isArray()) {
            for (JsonNode node : dataNode) {
                String sportName = node.has("originCompetitionName") ? node.get("originCompetitionName").asText() : "Football";
                Sport sport = sportRepository.findByName(sportName)
                        .orElseGet(() -> sportRepository.save(new Sport(null, sportName)));

                Team home = createTeamFromJson(node.get("homeTeam"));
                Team away = createTeamFromJson(node.get("awayTeam"));

                Event event = new Event();
                event.setDateVenue(LocalDate.parse(node.get("dateVenue").asText()));
                event.setTimeVenueUTC(LocalTime.parse(node.get("timeVenueUTC").asText()));
                event.setStatus(node.get("status").asText());
                event.setHomeGoals(node.get("homeGoals").asInt());
                event.setAwayGoals(node.get("awayGoals").asInt());
                event.setSport(sport);
                event.setHomeTeam(home);
                event.setAwayTeam(away);

                eventRepository.save(event);
            }
        }
        System.out.println("Database Seeded with Sportradar Data");
    }

    private Team createTeamFromJson(JsonNode teamNode) {
        if (teamNode == null || teamNode.isNull()) return null;
        String name = teamNode.get("name").asText();
        return teamRepository.findByName(name)
                .orElseGet(() -> {
                    Team team = new Team();
                    team.setName(name);
                    team.setOfficialName(teamNode.get("officialName").asText());
                    team.setAbbreviation(teamNode.get("abbreviation").asText());
                    team.setTeamCountryCode(teamNode.get("teamCountryCode").asText());
                    return teamRepository.save(team);
                });
    }
}