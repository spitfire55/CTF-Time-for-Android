package re.spitfy.ctftime.data.mapper

import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.entity.MemberEntity
import re.spitfy.ctftime.data.db.entity.ScoreEntity
import re.spitfy.ctftime.data.db.entity.TeamEntity
import re.spitfy.ctftime.data.db.entity.TeamMemberJoinEntity

fun List<Team>.toTeamEntities(): List<TeamEntity> = this.map {
    TeamEntity(
            id = it.id!!.toInt(),
            academic = it.Academic,
            countryCode = it.CountryCode,
            description = it.Description,
            email = it.Email,
            icq = it.ICQ,
            jabber = it.Jabber,
            linkedin = it.LinkedIn,
            logo = it.Logo,
            name = it.Name,
            nameCaseInsensitive = it.NameCaseInsensitive,
            skype = it.Skype,
            telegram = it.Telegram,
            twitter = it.Twitter,
            website = it.Website
    )
}

fun List<Team>.toScoreEntities(): List<ScoreEntity> {
    val scores = mutableListOf<ScoreEntity>()
    this.forEach {
        it.Scores.forEach { year, score ->
            scores.add(ScoreEntity(
                    year = year,
                    points = score.Points,
                    rank = score.Rank,
                    userId = it.id!!.toInt()
            ))
        }
    }
    return scores
}

fun List<Team>.toMemberEntities(): List<MemberEntity> {
    val members = mutableListOf<MemberEntity>()
    this.forEach {
        it.Members.forEach { member ->
            members.add(MemberEntity(
                    id = member.Id,
                    name = member.Name
            ))
        }
    }
    return members
}

fun List<Team>.toTeamMemberJoinEntities(): List<TeamMemberJoinEntity> {
    val teamMembersJoinEntities = mutableListOf<TeamMemberJoinEntity>()
    //TODO: Implement logic to populate teamMembersJoinEntities
    return teamMembersJoinEntities
}