.. |rd| replace:: :sup:`rd`


######################
TBML and Related Tools
######################

My ideas for TBML (Turn Based Markup Language) and tools that would make use of
it.

Background
==========

I have a strong interest in turn-based strategy games, as well as other, similar
descendents of *Chainmail*, *D&D*, and older wargames. I define these games as
follows:

- Play is divided into turns, during which exactly one player is making
  decisions and moves (but note that a move may consist of initiating a sub-game
  of some sort).

- Play takes place in a restricted environment called a "map", "dungeon", or
  "board". The map is divided into discrete sections called "tiles".

- Each player controls one or more "pieces", "units", or "characters", through
  which they interact with the other player(s). These units vary in what they
  can do.
  
Usually, there is a limit of one unit per tile, and units have some means of
interacting with each other, potentially removing one or both units from play.
Tiles often have traits of some sort which affect the units that occupy them.
Maps are generally two dimensional, with square or hex tiles.

These games can be divided into types based on the number of players and the
number of units each player controls. Since its 3\ |rd| edition, *D&D* has
generally had one player controlling an arbitrary number of units (the GM), as
well as at least one player controlling a single unit (the player), in a
structure that fits the above definition. Many other tabletop RPGs are similar.
Turn-based strategy games generally give all players control of many units. Most
are restricted to two players (of which one may be a computer), but some allow
many players. These games sometimes feature "campaigns", where the players go
through a series of games, with the results of each game in the campaign
affecting future games. The computer game *Wesnoth* is an easy to understand
example that makes use of a number of these features.

For a while now, I have wanted to design a language for specifing turn based
strategy games: their rules, a campaign using those rules, and any maps the
campaign uses. In reality, this language would need to two distinct parts: a
language for rules (which would also be used to specify campaigns), and a
language for maps. I was originally focused on making it easy for a designer to
draw and modify maps, but it occurred to me that the map-specification language
could be used to solve a problem that has plagued *D&D* for years: dungeon
generation.

A well-designed dungeon is an interesting, coherent, often 3-dimensional
environment, containing various traps and treasures, as well as a wide variety
of inhabitants for players to interact with. Any fights that happen ideally take
place in a location that makes the fight interesting in some way; failing that,
the environment needs to at least allow all the combatants to move around
without letting the fight drag on too long. Fights also need to be in a hard to
define "tough, but beatable" range of difficulty, where the players can win but
don't feel like their time is being wasted. Designing encounters and
environments that meet these restrictions is *hard*. There are a huge number of
soft constraints and very few hard ones. A tool that did at least some of the
work involved would be very useful.

Fundamental Requirements
========================

Before any interesting tools can be built, the following things need to be done:

- The map language needs to be fully specified, at least for the case of a 2D
  square grid. It should be able to describe an arbitrarily large map with an
  arbitrary number of different tile types, and should specify (perhaps
  implicitly) some general properties of the map like how big it is.

- The rule language needs enough of a grammar that tile properties can be
  specified, and that other useful things can be added on to it.

- Somewhere in the interaction of the map language and the rule language, there
  must be a way to define unit placements.

Once that's done, genuinely useful/interesting tools can be built.

Interesting Problems
====================

Designing a good encounter map.
   This is a complicated problem in its own right, without considering the
   placement of specific units. A good map should have natural fortresses and
   battlegrounds so that the encounter "flows" nicely - both sides have a clear
   objective within reach, but not achievable by a single unit in a single turn.

Designing a good encounter.
   Given rough estimates of unit strengths, locate each side's units such that
   neither dominates until the encounter is very nearly over. That is, both
   sides should be about even at the beginning, and there shouldn't be a long
   "mop up" phase at the end.

Keeping things coherent.
   Take some ecological-type constraints (provided by either the user or the
   previously designed encounters) and use those to design subsequent
   encounters. For example, if in the middle of a dungeon there's a goblin guard
   post, there should be something for them to guard, and probably some sense of
   where the other goblins are. If zombies generally have an origin, placing a
   zombie encounter should ential placing their origin somewhere relatively
   nearby. If you encounter a predator, you should run into its prey eventually.

Extra Niceties
==============

The more rules of the game can be factored out of the generator and pulled into
the language, the more useful the generator becomes. For example, movement rules
should ideally be part of the language, not an assumption made by the generator.
The more this is done, the more the generator is able to assess unit
capabilities on its own, rather than falling back on rough estimates provided by
the user. It would be even better if the generator could *provide* those
estimates, since games often use them for other purposes. That feeds back into
the second interesting problem mentioned above (and the first, to an extent),
but working on it depends on things that aren't particularly interesting or
essential.

In addition to a map generator, a basic "game driver" would be nice - something
that a GM could use to keep track of all of the state that goes on in a game.
This would also make testing much easier. However, it would involve a lot of
work on the "extra" problem above and would only indirectly do anything about
the "interesting" problems.
