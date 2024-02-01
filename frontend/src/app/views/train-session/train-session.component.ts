import { Component, OnInit } from '@angular/core';
import { CalendarOptions, DateSelectArg } from 'fullcalendar';
import { Calendrier } from '../../model/Calendrier.modul';
import { CalendrierService } from '../../shared/services/calendrier.service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGrigPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { MatDialog } from '@angular/material/dialog';
import { AddTraineComponent } from '../add-traine/add-traine.component';

@Component({
  selector: 'app-train-session',
  templateUrl: './train-session.component.html',
  styleUrls: ['./train-session.component.css']
})
export class TrainSessionComponent implements OnInit {
  events: Calendrier[] = [];
  calendarOptions: CalendarOptions = {
    initialView: 'timeGridWeek',
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },
    plugins: [dayGridPlugin, timeGrigPlugin, interactionPlugin],
    editable: true,
    selectable: true,
    select: this.handleDateSelect.bind(this),
  };

  constructor(private calendrierService: CalendrierService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  handleDateClick(arg: any) {
    console.log('Date clicked: ' + arg.dateStr);
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    console.log("Dialog open: ");
    console.log(selectInfo);
    console.log('Selection Start: ' + selectInfo.startStr);
    console.log('Selection End: ' + selectInfo.endStr);

    const dialogRef = this.dialog.open(AddTraineComponent, {
      width: '400px',
      data: {
        start: selectInfo.startStr,
        end: selectInfo.endStr
      }
    });
  }

  loadEvents(): void {
    this.calendrierService.getEvents().subscribe(
      (response: Calendrier[]) => {
        this.events = response;
        this.calendarOptions.events = this.events.map(event => ({
          title: event.title,
          start: new Date(event.datedebut),
          end: new Date(event.datefin),
        }));
      },
      error => {
        console.error('Erreur lors du chargement des événements :', error);
      }
    );
  }
}
